package com.example.websocket_demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.websocket_demo.config.interceptor.CustomInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") //요청을 허용할 출처(도메인)를 지정합니다. 
                .allowedMethods("GET", "POST", "PUT", "DELETE"); //허용할 HTTP 메서드(GET, POST 등)를 지정합니다. 
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor())
                .addPathPatterns("/**") //모든 요청 주소에 인터셉터를 적용하도록 설정합니다. 
                .excludePathPatterns("/login0001", "/error"); //경로에 해당하는 요청은 인터셉터에서 제외하도록 설정합니다. 
    }
    
    
}

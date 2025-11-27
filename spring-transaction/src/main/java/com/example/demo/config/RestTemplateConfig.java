package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	
	// RestTemplate : Spring에서 외부 API(HTTP 요청)를 보내기 위한 동기(블로킹) 방식의 HTTP 클라이언트
	// 동기방식 : HTTP 요청을 보내면 응답이 올 때까지 스레드가 기다리는 구조입니다.
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
	ApiClient apiClient(RestTemplate restTemplate, @Value("${api.base-url}") String apiBaseUrl, @Value("${api.key}") String apiKey) {
		return new ApiClient(restTemplate, apiBaseUrl, apiKey);
	}
}

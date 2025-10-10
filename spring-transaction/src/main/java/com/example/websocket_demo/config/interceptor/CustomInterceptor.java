package com.example.websocket_demo.config.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.websocket_demo.config.security.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//preHandler(): 컨트롤러 실행 전에 실행되며, false를 반환하면 컨트롤러를 실행하지 않습니다. 
//postHandler(): 컨트롤러가 정상적으로 실행된 후 뷰를 렌더링하기 전에 실행됩니다. 
//afterCompletion(): 뷰 렌더링 및 클라이언트 응답 전송이 완료된 후 실행됩니다. 
public class CustomInterceptor implements HandlerInterceptor {
	
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(">>> preHandle request getRequestURI : " + request.getRequestURI());
        System.out.println(">>> preHandle request getMethod  " + request.getMethod());
        System.out.println(">>> preHandle response : " + response);
        System.out.println(">>> handler request : " + handler);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!"anonymousUser".equals(auth.getName())) {
        	CustomUserDetails userDetails = (CustomUserDetails) auth.getAuthorities();
        	
        }
        
        return true; // false 리턴하면 Controller 진입 막음
    }

}

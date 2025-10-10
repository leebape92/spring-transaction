package com.example.websocket_demo.config.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PreLoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

    	
    	
    	System.out.println("[PreLoginFilter] 로그인 시도");

    	
        // 로그인 POST 요청만 처리
        if ("/login".equals(request.getServletPath()) && "POST".equalsIgnoreCase(request.getMethod())) {
            System.out.println("로그인 시도 전 커스텀 필터 실행!");
            
            // 예: IP 체크, CAPTCHA 검증, 요청 로깅 등 추가 가능
            String clientIp = request.getRemoteAddr();
            System.out.println("클라이언트 IP: " + clientIp);
        }

        // 다음 필터로 전달 (UsernamePasswordAuthenticationFilter 포함)
        filterChain.doFilter(request, response);
    }
}
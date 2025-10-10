package com.example.websocket_demo.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

    	
    	System.out.println(">>> Principal 클래스 = " + authentication.getPrincipal().getClass());
    	
        // 사용자 이름 가져오기
        String username = authentication.getName();

        // 로그인 성공 메시지
        String successMessage = username + "님 환영합니다!";

        System.out.println("[Login Success] " + successMessage);

        // 세션에 메시지 저장 (필요시)
        request.getSession().setAttribute("successMessage", successMessage);

        // 메인 페이지로 리다이렉트
        response.sendRedirect("/main");
    }
}


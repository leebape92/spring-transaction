package com.example.websocket_demo.config.security;

import java.io.IOException;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessage = "로그인 실패"; // 기본 메시지

        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "아이디가 존재하지 않습니다.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "비밀번호가 틀립니다.";
        } else if (exception instanceof LockedException) {
            errorMessage = "계정이 잠겨 있습니다.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "계정이 비활성화 되어 있습니다.";
        } else if (exception instanceof AccountExpiredException) {
            errorMessage = "계정이 만료되었습니다.";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "비밀번호가 만료되었습니다.";
        }

        // 콘솔 로그
        System.out.println("[Login Failure] " + errorMessage);
        
        // 사용이유
//        공식 문서에서도 AuthenticationFailureHandler에서 sendRedirect()와 세션/FlashMap 사용 권장
        request.getSession().setAttribute("loginErrorMessage", errorMessage);
        response.sendRedirect("/login0001");
    }
}


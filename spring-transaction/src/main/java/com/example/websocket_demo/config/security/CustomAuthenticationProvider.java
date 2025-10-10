package com.example.websocket_demo.config.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = (String) authentication.getCredentials();

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("[CustomAuthenticationProvider] 인증 시도 - username=" + username);
        
        

        // DB에서 사용자 조회 (없으면 UsernameNotFoundException 발생)
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        System.out.println("[CustomAuthenticationProvider] DB 조회 완료 - userDetails=" + userDetails.getUsername());

        System.out.println(">>> Principal 클래스 = " + userDetails.getClass());
        
        // 비밀번호 검증
        if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            System.err.println("[CustomAuthenticationProvider] 비밀번호 불일치 - username=" + username);
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // 계정 상태 검증
        if (!userDetails.isAccountNonLocked()) {
            System.err.println("[CustomAuthenticationProvider] 계정 잠김 - username=" + username);
            throw new LockedException("계정이 잠겨 있습니다.");
        }
        if (!userDetails.isEnabled()) {
            System.err.println("[CustomAuthenticationProvider] 계정 비활성화 - username=" + username);
            throw new DisabledException("계정이 비활성화 되어 있습니다.");
        }

        System.out.println("[CustomAuthenticationProvider] 인증 성공 - username=" + username);

        // 인증 성공 시 Authentication 반환
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null, // 인증 후 패스워드 제거
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

package com.example.websocket_demo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomLoginFailureHandler failureHandler; 
	private final CustomLoginSuccessHandler successHandler;

    //Authentication : 인증
    //Authorization  : 인가
	//permitAll()  : Spring Security에서 해당 URL이나 요청에 대해 인증 없이 접근 허용
    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Security 필터 체인 설정
	    return http
	        .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (학습용, 실제 서비스에서는 활성화 필요)
	        
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/public/**", "/login0001", "/regist", "/api/**", "/security-session-info").permitAll() // 인증 없이 접근 허용
	            .anyRequest().authenticated() // 그 외 모든 요청은 로그인(인증) 필요
	        )
	        
	        .formLogin(login -> login
	            .loginPage("/login0001") // 커스텀 로그인 페이지
	            .loginProcessingUrl("/login") // 로그인 처리 URL (POST 요청 인터셉트)
	            .defaultSuccessUrl("/main", true) // 로그인 성공 후 이동할 페이지
	            .failureUrl("/login0001?error=true") // 로그인 실패 시 이동할 페이지
	            .failureHandler(failureHandler) // 로그인 실패 시 실행할 핸들러
	            .successHandler(successHandler) // 로그인 성공 시 실행할 핸들러
	            .permitAll() // 로그인 페이지와 처리 URL은 인증 없이 접근 가능
	        )
	        
	        .logout(logout -> logout
	            .logoutUrl("/logout") // 로그아웃 요청 URL
	            .logoutSuccessUrl("/login0001?logout=true") // 로그아웃 성공 시 이동 페이지
	            .invalidateHttpSession(true) // 세션 무효화
	            .deleteCookies("JSESSIONID") // 브라우저 쿠키 제거
	            .permitAll() // 로그아웃 URL은 인증 없이 접근 가능
	        )
	        
	        .sessionManagement(session -> session
	            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요할 때만 세션 생성, 기존 세션 있으면 재사용
	            .invalidSessionUrl("/login0001?invalid=true") // 무효 세션 접근 시 이동할 페이지
	            .maximumSessions(1) // 한 계정 동시 로그인 최대 세션 수
	                .expiredUrl("/login0001?expired=true") // 동시 로그인 초과 시 이동할 페이지
	                .maxSessionsPreventsLogin(false) // 새 로그인 허용하면서 기존 세션 종료
	                .sessionRegistry(sessionRegistry()) // 동시 로그인 제한 및 세션 관리용
	        )
	        
	        .addFilterBefore(new PreLoginFilter(), UsernamePasswordAuthenticationFilter.class) // 로그인 전에 동작할 커스텀 필터
	        
	        .build(); // SecurityFilterChain 빌드
	}


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    // 등록하기전후 차이점
    // 포스트맨에서 탭을 2개 생성후에 로그인을한다
    // maximumSessions(1) 설정하였지만 세션이 2개가 생긴것을 확인할수있다
    // 즉 해당 클래스를 적용해야지 스프링 시큐리티가 세션 소멸 이벤트를 정상적으로 감지
    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

//

    
}
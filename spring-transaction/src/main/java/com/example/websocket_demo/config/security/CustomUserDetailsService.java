package com.example.websocket_demo.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.websocket_demo.user.entity.UserEntity;
import com.example.websocket_demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("[CustomUserDetailsService] loadUserByUsername 호출, username = {}", username);

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("[CustomUserDetailsService] 사용자 조회 실패, username = {}", username);
                    return new UsernameNotFoundException("아이디가 존재하지 않습니다: " + username);
                });

        log.debug("[CustomUserDetailsService] 사용자 조회 성공, username = {}", userEntity.getUsername());

        return new CustomUserDetails(userEntity);
    }
}

package com.fastcampus.board.config;

import com.fastcampus.board.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing // jpa auditing 
@Configuration
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware() { // 사용자 정보 받기
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal) // userdetail
                .map(BoardPrincipal.class::cast)
                .map(BoardPrincipal::getUsername);
    }
}

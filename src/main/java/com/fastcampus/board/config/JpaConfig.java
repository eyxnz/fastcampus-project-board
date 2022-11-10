package com.fastcampus.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing // jpa auditing 
@Configuration
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("2yxxn"); // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 때, 수정할 예정
    }
}

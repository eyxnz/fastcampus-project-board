package com.fastcampus.board.config;

import com.fastcampus.board.dto.UserAccountDto;
import com.fastcampus.board.dto.security.BoardPrincipal;
import com.fastcampus.board.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 스프링 시큐리티 검사에서 제외하겠다 - static resources
                        .mvcMatchers(
                                HttpMethod.GET,
                                "/",
                                "/articles",
                                "/articles/search-hashtag",
                                "/signup"
                        ).permitAll() // 인증에 상관 없이 공개하겠다
                        .anyRequest().authenticated() // 나머지는 인증이 필요
                )
                .formLogin().and()
                .logout().logoutSuccessUrl("/").and()
                .build();
    }

    /*
    // 요새는 권장되는 방식이 아님
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 스프링 시큐리티 검사에서 제외하겠다
        // static resources
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    */

    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
        return username -> userAccountRepository
                .findById(username)
                .map(UserAccountDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username : " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

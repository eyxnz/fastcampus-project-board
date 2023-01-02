package com.fastcampus.board.config;

import com.fastcampus.board.domain.UserAccount;
import com.fastcampus.board.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
    @MockBean private UserAccountRepository userAccountRepository;

    @BeforeTestMethod // spring test 를 쓴다면 각 test method 가 실행되기 전에 먼저 실행해라
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString()))
                .willReturn(Optional.of(UserAccount.of(
                        "eyxnzTest",
                        "pw",
                        "eyxnz-test@email.com",
                        "eyxnz-test",
                        "test memo"
                )));
    }
}

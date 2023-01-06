package com.fastcampus.board.service;

import com.fastcampus.board.dto.UserAccountDto;
import com.fastcampus.board.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SignupService {
    private final UserAccountRepository userAccountRepository;

    public void saveUserAccount(UserAccountDto dto) throws Exception {

    }
}

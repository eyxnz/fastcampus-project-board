package com.fastcampus.board.dto.request;

import com.fastcampus.board.dto.UserAccountDto;

public record UserAccountRequest(
        String userId,
        String userPassword,
        String email,
        String nickname,
        String memo
) {
    public static UserAccountRequest of(String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccountRequest(userId, userPassword, email, nickname, memo);
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                userId,
                userPassword,
                email,
                nickname,
                memo
        );
    }
}

package com.fastcampus.board.controller;

import com.fastcampus.board.dto.request.UserAccountRequest;
import com.fastcampus.board.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/signup")
@Controller
public class SignupController {
    private final SignupService signupService;

    @GetMapping
    public String signupForm() {
        return "/signup";
    }

    @PostMapping
    public String signup(UserAccountRequest userAccountRequest) throws Exception {
        signupService.saveUserAccount(userAccountRequest.toDto());

        return "redirect:/articles";
    }
}

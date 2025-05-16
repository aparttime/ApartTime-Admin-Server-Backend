package com.aparttime.auth.controller;

import com.aparttime.auth.dto.request.SignupRequest;
import com.aparttime.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signup")
    public String showSignupForm(
        Model model
    ) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String processSignup(
        @ModelAttribute SignupRequest signupRequest
    ) {
        authService.signup(signupRequest);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String showLoginPage(
        @RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "logout", required = false) String logout,
        Model model
    ) {
        if (error != null) {
            model.addAttribute(
                "errorMessage",
                "아이디 또는 비밀번호가 잘못되었습니다."
            );
        }

        if (logout != null) {
            model.addAttribute(
                "logoutMessage",
                "성공적으로 로그아웃되었습니다."
            );
        }

        return "auth/login";
    }

}

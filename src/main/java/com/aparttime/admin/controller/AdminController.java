package com.aparttime.admin.controller;

import com.aparttime.admin.dto.request.AdminSignupRequest;
import com.aparttime.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("adminSignupRequest", new AdminSignupRequest());
        return "admin/signup";
    }

}

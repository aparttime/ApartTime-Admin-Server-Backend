package com.aparttime.dashboard.controller;

import com.aparttime.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String showDashboard(
        Model model
    ) {
        long pendingCount = dashboardService.getPendingSignupCount();
        model.addAttribute("pendingCount", pendingCount);
        return "dashboard/dashboard";
    }

}

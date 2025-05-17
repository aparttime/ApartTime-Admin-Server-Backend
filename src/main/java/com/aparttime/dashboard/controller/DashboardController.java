package com.aparttime.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class DashboardController {

    // private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard/dashboard";
    }

}

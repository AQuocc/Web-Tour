package com.travel.controller;

import com.travel.model.User;
import com.travel.service.UserService;
import com.travel.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/statistics")
    public String viewStatistics(Model model) {
        model.addAttribute("stats", statisticsService.getStatistics());
        return "admin/statistics";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/user/list";
    }

    @PostMapping("/users/{userId}/toggle-status")
    public String toggleUserStatus(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        try {
            userService.toggleUserStatus(userId);
            redirectAttributes.addFlashAttribute("success", "User status updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update user status: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{userId}/update-role")
    public String updateUserRole(@PathVariable Long userId, @RequestParam String role, RedirectAttributes redirectAttributes) {
        try {
            userService.updateUserRole(userId, role);
            redirectAttributes.addFlashAttribute("success", "User role updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update user role: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}
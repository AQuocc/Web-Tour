package com.travel.controller;

import com.travel.model.User;
import com.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.registerNewUser(user);
            return "redirect:/login?registered";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Authentication authentication) {
        User currentUser = userService.findByUsername(authentication.getName());
        model.addAttribute("user", currentUser);
        return "auth/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute("user") User user,
                              BindingResult result,
                              Model model,
                              Authentication authentication) {
        if (result.hasErrors()) {
            return "auth/profile";
        }

        try {
            User currentUser = userService.findByUsername(authentication.getName());
            // Update only allowed fields
            currentUser.setFullName(user.getFullName());
            currentUser.setEmail(user.getEmail());
            currentUser.setPhoneNumber(user.getPhoneNumber());
            
            userService.updateUser(currentUser);
            model.addAttribute("success", "Profile updated successfully");
            model.addAttribute("user", currentUser);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "auth/profile";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam String currentPassword,
                               @RequestParam String newPassword,
                               @RequestParam String confirmPassword,
                               Model model,
                               Authentication authentication) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("passwordError", "New passwords do not match");
            return "auth/profile";
        }

        try {
            userService.changePassword(authentication.getName(), currentPassword, newPassword);
            model.addAttribute("passwordSuccess", "Password changed successfully");
        } catch (RuntimeException e) {
            model.addAttribute("passwordError", e.getMessage());
        }

        // Reload user data for the profile page
        User currentUser = userService.findByUsername(authentication.getName());
        model.addAttribute("user", currentUser);
        return "auth/profile";
    }
}
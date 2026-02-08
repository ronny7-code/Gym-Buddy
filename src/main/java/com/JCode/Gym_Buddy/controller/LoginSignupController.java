package com.JCode.Gym_Buddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.JCode.Gym_Buddy.entity.GymMember;

@Controller
public class LoginSignupController {

    // Login page
     @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    
    // Registration page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("gymMember", new GymMember());
        return "register";
    }
}

package com.JCode.Gym_Buddy.controller;

import com.JCode.Gym_Buddy.dto.GymMemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.JCode.Gym_Buddy.service.GymMemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginSignupController {

    private final GymMemberService gymMemberService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("gymMember", new GymMemberDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerMember(
            @Valid @ModelAttribute("gymMember") GymMemberDto gymMember,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        // 1. Validation errors
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // 2. Try saving user (DTO passed directly)
        boolean isAdded = gymMemberService.addMember(gymMember);

        // 3. Success
        if (isAdded) {
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Registration successful! Please login."
            );
            return "redirect:/login";
        }

        // 4. Failure (duplicate email / username / phone)
        model.addAttribute(
                "error",
                "User is not registered. Email, username, or phone already exists."
        );

        return "register";
    }


}
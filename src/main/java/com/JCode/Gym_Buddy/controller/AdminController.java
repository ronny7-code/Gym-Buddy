package com.JCode.Gym_Buddy.controller;

import com.JCode.Gym_Buddy.dto.AdminDto;
import com.JCode.Gym_Buddy.dto.TrainerDto;
import com.JCode.Gym_Buddy.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @ModelAttribute("adminName")
    public String getAdminName(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return null;
        return adminService.getByUsername(userDetails.getUsername()).getName();
    }

    @GetMapping({"/home", "/dashboard"})
    public String getAdminDashboard() {
        return "admin/adminHome";
    }

    @GetMapping("/add")
    public String getAdminRegisterPage(Model model) {
        model.addAttribute("adminDto", new AdminDto());
        return "admin/adminRegister";
    }

    @PostMapping("/register")
    public String registerAdmin(@Valid @ModelAttribute AdminDto adminDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/adminRegister";
        }

        if (!adminService.addAdmin(adminDto)) {
            model.addAttribute("error", "Admin already registered with this email or username!");
            return "admin/adminRegister";
        }

        model.addAttribute("success", "New Admin added successfully!");
        model.addAttribute("adminDto", new AdminDto());
        return "admin/adminRegister";
    }

    @GetMapping("/addTrainer")
    public String getTrainerRegisterPage(Model model){
        model.addAttribute("trainerDto", new TrainerDto());
        return "admin/addTrainer";
    }

    
}
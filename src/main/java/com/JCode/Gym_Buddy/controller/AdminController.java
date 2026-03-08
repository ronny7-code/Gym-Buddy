package com.JCode.Gym_Buddy.controller;

import com.JCode.Gym_Buddy.dto.AdminDto;
import com.JCode.Gym_Buddy.dto.TrainerDto;
import com.JCode.Gym_Buddy.service.AdminService;
import com.JCode.Gym_Buddy.service.TrainerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final TrainerService trainerService;

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

    @GetMapping("/trainer/add")
    public String getTrainerRegisterPage(Model model) {
        model.addAttribute("trainerDto", new TrainerDto());
        return "admin/addTrainer";
    }

    @PostMapping("/trainer/add")
    public String addNewTrainer(@Valid @ModelAttribute TrainerDto trainerDto,
                                BindingResult bindingResult,
                                Model model,
                                @RequestParam MultipartFile profileImageFile,
                                @RequestParam String expertInRaw) throws IOException {

        trainerDto.setExpertIn(expertInRaw.trim());
        trainerDto.setAvailable(true);

        if (bindingResult.hasErrors()) {
            return "admin/addTrainer";
        }

        if (profileImageFile.isEmpty()) {
            model.addAttribute("error", "Profile image is required!");
            return "admin/addTrainer";
        }

        String originalName = profileImageFile.getOriginalFilename().toLowerCase();
        if (!originalName.endsWith(".jpg") && !originalName.endsWith(".jpeg") && !originalName.endsWith(".png")) {
            model.addAttribute("error", "Only JPG and PNG images are allowed!");
            return "admin/addTrainer";
        }

        String fileName = UUID.randomUUID().toString().substring(0, 8) + "_" + profileImageFile.getOriginalFilename();
        String uploadDir = "uploads/images/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        profileImageFile.transferTo(uploadPath.resolve(fileName));
        trainerDto.setProfileImageName(fileName);

        trainerService.addTrainer(trainerDto);
        model.addAttribute("success", "Trainer added successfully!");
        model.addAttribute("trainerDto", new TrainerDto());
        return "admin/addTrainer";
    }
}   
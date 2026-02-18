package com.JCode.Gym_Buddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.JCode.Gym_Buddy.dto.GymMemberDto;
import com.JCode.Gym_Buddy.entity.ContactForm;
import com.JCode.Gym_Buddy.service.GymMemberService;

import jakarta.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.JCode.Gym_Buddy.service.ContactFormService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final GymMemberService gymMemberService;
    private final ContactFormService contactFormService;

    @GetMapping("/profile")
    public String getMemberProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        GymMemberDto member = gymMemberService.findMemberByUsername(userDetails.getUsername());

        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);
        return "member/memberProfile";
    }

    @GetMapping("/aboutUs")
    public String getMemberAboutPage() {
        return "member/memberAbout";
    }

    @GetMapping("/contact")
    public String getMemberContactPage(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "member/memberContact";
    }

    @GetMapping("/team")
    public String getMemberTeamPage() {
        return "member/memberTeam";
    }

    @GetMapping("/services")
    public String getMemberServicesPage() {
        return "member/memberServices";
    }

    @GetMapping("/profile/update") 
    public String showUpdateProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        GymMemberDto member = gymMemberService.findMemberByUsername(userDetails.getUsername());
        if (member == null) {
            return "redirect:/login";
        }
        model.addAttribute("member", member);
        return "member/memberProfileUpdate";
    }

    @PostMapping("/profile/update") 
    public String saveUpdateProfile(
            @Valid @ModelAttribute("member") GymMemberDto memberDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "member/memberProfileUpdate";
        }

        boolean success = gymMemberService.updateMember(memberDto.getId(), memberDto);

        if (success) {
            redirectAttributes.addFlashAttribute("success", "Profile updated! ");
        } else {
            redirectAttributes.addFlashAttribute("error", "Update failed ");
        }

        return "redirect:/member/profile";
    }

}

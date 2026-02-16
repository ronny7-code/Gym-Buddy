package com.JCode.Gym_Buddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.JCode.Gym_Buddy.dto.GymMemberDto;
import com.JCode.Gym_Buddy.entity.ContactForm;
import com.JCode.Gym_Buddy.service.GymMemberService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    // Handling contact form submission
    @PostMapping("/contact")
    public String contactForm(@ModelAttribute ContactForm contactForm,
            RedirectAttributes redirectAttributes) {
        boolean saved = contactFormService.saveContactForm(contactForm);
        if (saved) {
            redirectAttributes.addFlashAttribute("success", "Message sent successfully!");
            return "redirect:/member/contact";
        }
        redirectAttributes.addFlashAttribute("error", "Failed to send message");
        return "redirect:/member/contact";
    }
}

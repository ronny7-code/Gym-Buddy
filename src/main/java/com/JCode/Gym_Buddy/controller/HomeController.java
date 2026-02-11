package com.JCode.Gym_Buddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.JCode.Gym_Buddy.entity.ContactForm;
import com.JCode.Gym_Buddy.service.ContactFormService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final ContactFormService contactFormService;

    // Home page
    @GetMapping({"/", "/home"})
    public String getHomePage() {
        return "home";
    }

    // About Us page
    @GetMapping("/aboutUs")
    public String getAboutUsPage() {
        return "about";
    }

    // Services page
    @GetMapping("/services")
    public String getServicesPage() {
        return "services";
    }

    // Contact page
    @GetMapping("/contact")
    public String getContactPage(Model model) {
        model.addAttribute("contactForm", new ContactForm()); 
        return "contact";
    }
    
    // Team page
    @GetMapping("/team")
    public String getTeamPage() {
        return "team";
    }

    // Handling contact form submission
    @PostMapping("/contact")
    public String contactForm(@ModelAttribute ContactForm contactForm,
            RedirectAttributes redirectAttributes) {
        boolean saved = contactFormService.saveContactForm(contactForm);
        if (saved) {
            redirectAttributes.addFlashAttribute("success", "Message sent successfully!");
            return "redirect:/contact";
        }
        redirectAttributes.addFlashAttribute("error", "Failed to send message");
        return "redirect:/contact";
    }
}
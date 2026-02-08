package com.JCode.Gym_Buddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.JCode.Gym_Buddy.entity.ContactForm;


@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/aboutUs")
    public String getAboutUsPage() {
        return "about";
    }

    @GetMapping("/services")
    public String getServicesPage() {
        return "services";
    }

    @GetMapping("/contact")
    public String getContactPage(Model model) {
        model.addAttribute("contactForm", new ContactForm()); 
        return "contact";
    }
    
    @GetMapping("/team")
    public String getTeamPage() {
        return "team";
    }
    
}

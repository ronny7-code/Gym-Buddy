package com.JCode.Gym_Buddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.JCode.Gym_Buddy.entity.ContactForm;
import com.JCode.Gym_Buddy.service.ContactFormService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactFormService contactFormService;

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

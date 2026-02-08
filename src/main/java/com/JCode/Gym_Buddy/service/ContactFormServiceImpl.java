package com.JCode.Gym_Buddy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.JCode.Gym_Buddy.entity.ContactForm;
import com.JCode.Gym_Buddy.repository.ContactFormRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactFormServiceImpl implements ContactFormService {

    private final ContactFormRepo contactFormRepo;

    @Override
    @Transactional
    public boolean saveContactForm(ContactForm contactForm) {
        try {
            contactFormRepo.save(contactForm);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

}

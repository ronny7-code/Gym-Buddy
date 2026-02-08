package com.JCode.Gym_Buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JCode.Gym_Buddy.entity.ContactForm;

public interface ContactFormRepo extends JpaRepository<ContactForm, Long> {

}

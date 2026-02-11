package com.JCode.Gym_Buddy.repository;

import com.JCode.Gym_Buddy.entity.Admin;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmail(@Email String email);
    Admin findByUsername(String username);
}
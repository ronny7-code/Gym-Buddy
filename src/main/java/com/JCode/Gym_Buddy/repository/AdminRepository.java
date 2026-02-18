package com.JCode.Gym_Buddy.repository;

import com.JCode.Gym_Buddy.entity.Admin;
import jakarta.validation.constraints.Email;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmail(@Email String email);
    Optional<Admin> findByUsername(String username);
}
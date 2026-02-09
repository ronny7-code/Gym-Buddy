package com.JCode.Gym_Buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JCode.Gym_Buddy.entity.GymMember;

@Repository
public interface GymMemberRepo extends JpaRepository<GymMember, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    GymMember findByEmail(String email);
    GymMember findByUsername(String username);
}
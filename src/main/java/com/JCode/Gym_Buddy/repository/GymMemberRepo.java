package com.JCode.Gym_Buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JCode.Gym_Buddy.entity.GymMember;

public interface GymMemberRepo extends JpaRepository<GymMember, Long> {

}

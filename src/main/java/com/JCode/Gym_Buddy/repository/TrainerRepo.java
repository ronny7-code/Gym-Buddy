package com.JCode.Gym_Buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JCode.Gym_Buddy.entity.Trainer;

public interface TrainerRepo extends JpaRepository<Trainer, Long>{

}

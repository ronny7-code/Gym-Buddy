package com.JCode.Gym_Buddy.service;

import java.util.List;

import com.JCode.Gym_Buddy.dto.TrainerDto;

public interface TrainerService {

    boolean addTrainer(TrainerDto trainerDto);
    List<TrainerDto> getAllTrainers();
    TrainerDto getTrainerById(Long id);
    boolean updateTrainer(Long id, TrainerDto toUpdate);
    boolean deleteTrainer(Long id);

}
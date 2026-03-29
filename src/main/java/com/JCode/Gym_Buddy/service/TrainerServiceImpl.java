package com.JCode.Gym_Buddy.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.JCode.Gym_Buddy.dto.TrainerDto;
import com.JCode.Gym_Buddy.entity.Trainer;
import com.JCode.Gym_Buddy.repository.TrainerRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepo trainerRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TrainerDto> getAllTrainers() {
        return trainerRepo.findAll()
                .stream()
                .map(trainer -> modelMapper.map(trainer, TrainerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerDto getTrainerById(Long id) {
        return trainerRepo.findById(id)
                .map(trainer -> modelMapper.map(trainer, TrainerDto.class))
                .orElse(null);
    }

    @Override
    @Transactional
    public boolean addTrainer(TrainerDto trainerDto) {
        try {
            Trainer trainer = modelMapper.map(trainerDto, Trainer.class);
            trainerRepo.save(trainer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateTrainer(Long id, TrainerDto toUpdate) {
        Trainer existing = trainerRepo.findById(id).orElse(null);
        if (existing == null) {
            return false;
        }
        existing.setName(toUpdate.getName());
        existing.setEmail(toUpdate.getEmail());
        existing.setPhoneNumber(toUpdate.getPhoneNumber());
        existing.setRole(toUpdate.getRole());
        existing.setExpertIn(toUpdate.getExpertIn());
        existing.setYearsOfExperience(toUpdate.getYearsOfExperience());
        existing.setBio(toUpdate.getBio());
        existing.setProfileImageName(toUpdate.getProfileImageName());

        trainerRepo.save(existing);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteTrainer(Long id) {
        if (!trainerRepo.existsById(id)){
            return false;
        }
        trainerRepo.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalTrainers() {
        return (int) trainerRepo.count();
    }
}
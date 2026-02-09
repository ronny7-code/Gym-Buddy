package com.JCode.Gym_Buddy.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.JCode.Gym_Buddy.entity.GymMember;
import com.JCode.Gym_Buddy.repository.GymMemberRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GymMemberServiceImpl implements GymMemberService {

    private final GymMemberRepo gymMemberRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<GymMember> getAllMembers() {
        return gymMemberRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public GymMember getMemberById(Long memberId) {
        return gymMemberRepo.findById(memberId).orElse(null);
    }

    @Override
    @Transactional
    public boolean addMember(GymMember gymMember) {
        try {
            gymMember.setPassword(passwordEncoder.encode(gymMember.getPassword()));
            gymMemberRepo.save(gymMember);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateMember(Long memberId, GymMember updatedMember) {
        GymMember existingMember = getMemberById(memberId);

        if (existingMember == null) return false;

        existingMember.setName(updatedMember.getName());
        existingMember.setEmail(updatedMember.getEmail());
        existingMember.setPhoneNumber(updatedMember.getPhoneNumber());
        existingMember.setAge(updatedMember.getAge());
        existingMember.setMembershipType(updatedMember.getMembershipType());
        existingMember.setUsername(updatedMember.getUsername());

        if (updatedMember.getPassword() != null && !updatedMember.getPassword().isBlank()) {
            existingMember.setPassword(
                    passwordEncoder.encode(updatedMember.getPassword())
            );
        }

        gymMemberRepo.save(existingMember);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteMember(Long memberId) {
        if (!gymMemberRepo.existsById(memberId)) return false;
        gymMemberRepo.deleteById(memberId);
        return true;
    }
}

package com.JCode.Gym_Buddy.service;

import com.JCode.Gym_Buddy.dto.GymMemberDto;
import com.JCode.Gym_Buddy.entity.GymMember;
import com.JCode.Gym_Buddy.repository.GymMemberRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GymMemberServiceImpl implements GymMemberService {

    private final GymMemberRepo gymMemberRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GymMemberDto> getAllMembers() {
        return gymMemberRepo.findAll()
                .stream()
                .map(member -> modelMapper.map(member, GymMemberDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GymMemberDto getMemberById(Long memberId) {
        return gymMemberRepo.findById(memberId)
                .map(member -> modelMapper.map(member, GymMemberDto.class))
                .orElse(null);
    }

    @Override
    @Transactional
    public boolean addMember(GymMemberDto gymMemberDto) {
        try {
            // Map DTO → Entity
            GymMember member = modelMapper.map(gymMemberDto, GymMember.class);

            // Encode password
            member.setPassword(passwordEncoder.encode(member.getPassword()));

            member.setRole("ROLE_MEMBER");
            gymMemberRepo.save(member);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateMember(Long memberId, GymMemberDto updatedMemberDto) {
        GymMember existingMember = gymMemberRepo.findById(memberId).orElse(null);
        if (existingMember == null){
            return false;
        }
        // Update fields
        existingMember.setName(updatedMemberDto.getName());
        existingMember.setEmail(updatedMemberDto.getEmail());
        existingMember.setPhoneNumber(updatedMemberDto.getPhoneNumber());
        existingMember.setAge(updatedMemberDto.getAge());
        existingMember.setMembershipType(updatedMemberDto.getMembershipType());
        existingMember.setUsername(updatedMemberDto.getUsername());

        if (updatedMemberDto.getPassword() != null && !updatedMemberDto.getPassword().isBlank()) {
            existingMember.setPassword(passwordEncoder.encode(updatedMemberDto.getPassword()));
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

@Override
@Transactional(readOnly = true)
public GymMemberDto findMemberByUsername(String username) {
    return gymMemberRepo.findByUsername(username)
            .map(member -> modelMapper.map(member, GymMemberDto.class))
            .orElse(null);
}
}
package com.JCode.Gym_Buddy.service;

import java.util.List;

import com.JCode.Gym_Buddy.dto.GymMemberDto;

public interface GymMemberService {

    List<GymMemberDto> getAllMembers();
    GymMemberDto getMemberById(Long memberId);
    boolean addMember(GymMemberDto gymMemberDto);
    boolean updateMember(Long memberId, GymMemberDto updatedMember);
    boolean deleteMember(Long memberId);
    GymMemberDto findMemberByUsername(String username);
}
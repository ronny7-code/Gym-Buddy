package com.JCode.Gym_Buddy.service;

import java.util.List;

import com.JCode.Gym_Buddy.entity.GymMember;

public interface GymMemberService {

    List<GymMember> getAllMembers();
    GymMember getMemberById(Long memberId);
    boolean addMember(GymMember gymMember);
    boolean updateMember(Long memberId, GymMember updatedMember);
    boolean deleteMember(Long memberId);
}

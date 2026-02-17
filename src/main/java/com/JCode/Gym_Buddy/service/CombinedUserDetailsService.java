package com.JCode.Gym_Buddy.service;

import com.JCode.Gym_Buddy.entity.Admin;
import com.JCode.Gym_Buddy.entity.GymMember;
import com.JCode.Gym_Buddy.repository.AdminRepository;
import com.JCode.Gym_Buddy.repository.GymMemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CombinedUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final GymMemberRepo memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Try to find admin first
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
            return new User(admin.getUsername(), admin.getPassword(), Collections.singleton(authority));
        }

        // If not found, try to find GymMember
        GymMember member = memberRepository.findByUsername(username).orElse(null);
        if (member != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_MEMBER");
            return new User(member.getUsername(), member.getPassword(), Collections.singleton(authority));
        }

        // Not found in either
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
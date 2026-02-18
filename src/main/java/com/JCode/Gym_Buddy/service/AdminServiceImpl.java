package com.JCode.Gym_Buddy.service;

import com.JCode.Gym_Buddy.dto.AdminDto;
import com.JCode.Gym_Buddy.entity.Admin;
import com.JCode.Gym_Buddy.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    // Add admin
    @Override
    @Transactional
    public boolean addAdmin(AdminDto adminDto) {
        if (adminRepository.existsByEmail(adminDto.getEmail())) {
            return false;
        }

        // Map DTO to Entity
        Admin admin = mapper.map(adminDto, Admin.class);

        // Encode password
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        // Set role: default to ROLE_ADMIN if not provided
        if (admin.getRole() == null || admin.getRole().isBlank()) {
            admin.setRole("ROLE_ADMIN");
        }

        // Save to DB
        adminRepository.save(admin);
        return true;
    }

    // Get all admins
    @Override
    @Transactional(readOnly = true)
    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(admin -> mapper.map(admin, AdminDto.class))
                .collect(Collectors.toList());
    }

    // Get single admin by their id
    @Override
    @Transactional(readOnly = true)
    public AdminDto getAdminById(Long id) {
        return adminRepository.findById(id)
                .map(admin -> mapper.map(admin, AdminDto.class))
                .orElse(null);
    }

    // Update admin
    @Override
    @Transactional
    public boolean updateAdmin(Long id, AdminDto newAdmin) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin == null) {
            return false;
        }

        admin.setEmail(newAdmin.getEmail());
        admin.setName(newAdmin.getName());
        admin.setUsername(newAdmin.getUsername());

        if (newAdmin.getPassword() != null && !newAdmin.getPassword().isBlank()) {
            admin.setPassword(passwordEncoder.encode(newAdmin.getPassword()));
        }

        if (newAdmin.getRole() != null && !newAdmin.getRole().isBlank()) {
            admin.setRole(newAdmin.getRole());
        }

        adminRepository.save(admin); 
        return true;
    }

    // Delete admin
    @Override
    @Transactional
    public boolean deleteAdmin(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Find admin by their username
    @Override
    @Transactional(readOnly = true)
    public AdminDto getByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username).orElse(null);
        if(admin != null){
        AdminDto adminDto = mapper.map(admin, AdminDto.class);
        return adminDto;
        }
        return null;
    }
}
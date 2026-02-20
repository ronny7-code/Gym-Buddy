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

    @Override
    @Transactional
    public boolean addAdmin(AdminDto adminDto) {
        if (isAdminExists(adminDto.getEmail(), adminDto.getUsername())) {
            return false;
        }

        Admin admin = mapper.map(adminDto, Admin.class);
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword())); 
        if (admin.getRole() == null || admin.getRole().isBlank()) {
            admin.setRole("ROLE_ADMIN");
        }
        adminRepository.save(admin);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(admin -> mapper.map(admin, AdminDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDto getAdminById(Long id) {
        return adminRepository.findById(id)
                .map(admin -> mapper.map(admin, AdminDto.class))
                .orElse(null);
    }

    @Override
    @Transactional
    public boolean updateAdmin(Long id, AdminDto newAdmin) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin == null) return false;

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

    @Override
    @Transactional
    public boolean deleteAdmin(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDto getByUsername(String username) {
        return adminRepository.findByUsername(username)
                .map(admin -> mapper.map(admin, AdminDto.class))
                .orElse(null);
    }

    @Override
    public boolean isAdminExists(String email, String username) {
        return adminRepository.existsByEmail(email) || adminRepository.existsByUsername(username);
    }
}

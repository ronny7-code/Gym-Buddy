package com.JCode.Gym_Buddy.service;

import com.JCode.Gym_Buddy.dto.AdminDto;

import java.util.List;

public interface AdminService {

    boolean addAdmin(AdminDto admin);
    List<AdminDto> getAllAdmins();
    AdminDto getAdminById(Long id);
    boolean updateAdmin(Long id, AdminDto newAdmin);
    boolean deleteAdmin(Long id);
    AdminDto getByUsername(String username);

}
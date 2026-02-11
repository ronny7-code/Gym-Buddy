package com.JCode.Gym_Buddy.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GymMemberDto {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;

    @Min(16)
    @Max(100)
    private int age;

    @NotBlank
    private String membershipType;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String role;
}
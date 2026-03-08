package com.JCode.Gym_Buddy.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDto {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^9[0-9]{9}$", message = "Phone number must be 10 digits and start with 9")
    private String phoneNumber;

    @NotBlank(message = "Role is required")
    @Size(max = 50, message = "Role must not exceed 50 characters")
    private String role;

    private String expertIn;

    @Min(value = 0, message = "Years of experience cannot be negative")
    @Max(value = 50, message = "Years of experience seems unrealistic above 50")
    private int yearsOfExperience;

    @NotBlank(message = "Bio is required")
    @Size(max = 500, message = "Bio must not exceed 500 characters")
    private String bio;

    private String profileImageName;
    private boolean isAvailable;
}
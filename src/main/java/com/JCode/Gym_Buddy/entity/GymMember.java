package com.JCode.Gym_Buddy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GymMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(unique = true)
    private String phoneNumber;

    @Min(value = 16, message = "Minimum age is 16")
    @Max(value = 100, message = "Maximum age is 100")
    private int age;

    @NotBlank(message = "Membership type is required")
    private String membershipType;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be 3–50 characters")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 3, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank
    private String role;
}
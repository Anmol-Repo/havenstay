package com.havenstay.dto;

import com.havenstay.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO {

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

     @NotNull(message = "Email is required")
    private String email;

     @NotNull(message = "Phone number is required")
    private String phoneNumber;

     private UserRole role;

    @NotNull(message = "Password is required")
    private String password;
}

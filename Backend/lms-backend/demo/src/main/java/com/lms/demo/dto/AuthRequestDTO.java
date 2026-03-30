package com.lms.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequestDTO
{
    @NotBlank(message = "email should not be empty")
    private String email;
    @NotBlank(message = "password should not be empty")
    @Size(min=6,message = "Password must be at least 6 characters")
    private String password;
}

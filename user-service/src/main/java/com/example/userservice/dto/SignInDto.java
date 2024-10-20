package com.example.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInDto(

        @Size(min = 5, max = 64, message = "Username must be between 5 and 64 characters long")
        @NotBlank(message = "Username cannot be empty")
        String username,

        @Size(min = 4, max = 255, message = "Password length must be between 8 and 255 characters")
        @NotBlank(message = "Password cannot be empty")
        String password) {

}

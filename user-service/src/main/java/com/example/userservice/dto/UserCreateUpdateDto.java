package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collection;

public record UserCreateUpdateDto(

        @Size(min = 5, max = 64, message = "Username must be between 5 and 64 characters long")
        @NotBlank(message = "Username cannot be empty")
        String username,

        @Size(min = 5, max = 128, message = "Email address must be between 5 and 128 characters long")
        @NotBlank(message = "Email address cannot be empty")
        @Email(message = "The email address must be in the format user@example.com")
        String email,

        @Size(min = 8, max = 255, message = "Password length must be between 8 and 255 characters")
        @NotBlank(message = "Password cannot be empty")
        String password,

        @Size(min = 5, max = 128, message = "Firstname must be between 5 and 128 characters long")
        @NotBlank(message = "Username cannot be empty")
        String firstname,

        @Size(min = 5, max = 128, message = "Last must be between 5 and 128 characters long")
        @NotBlank(message = "Username cannot be empty")
        String lastname,

        Collection<String> roles) {

}

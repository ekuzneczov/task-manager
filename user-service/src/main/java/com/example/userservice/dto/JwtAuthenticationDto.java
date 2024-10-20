package com.example.userservice.dto;

import jakarta.validation.constraints.NotNull;

public record JwtAuthenticationDto(@NotNull String token) {

}

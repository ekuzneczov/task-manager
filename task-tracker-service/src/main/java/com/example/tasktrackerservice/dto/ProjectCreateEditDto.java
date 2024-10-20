package com.example.tasktrackerservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectCreateEditDto(@NotBlank String name) {
}

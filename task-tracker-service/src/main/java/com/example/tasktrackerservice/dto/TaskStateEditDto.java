package com.example.tasktrackerservice.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskStateEditDto(@NotBlank String name,
                               Long leftTaskStateId,
                               Long rightTaskStateId) {
}

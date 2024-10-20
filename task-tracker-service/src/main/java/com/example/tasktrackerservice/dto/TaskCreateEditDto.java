package com.example.tasktrackerservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskCreateEditDto(@NotNull Long taskStateId,
                                @NotBlank String name,
                                String description) {
}
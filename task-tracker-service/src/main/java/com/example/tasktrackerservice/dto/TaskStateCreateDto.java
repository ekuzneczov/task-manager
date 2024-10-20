package com.example.tasktrackerservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskStateCreateDto(@NotNull Long projectId,
                                 @NotBlank String name) {
}

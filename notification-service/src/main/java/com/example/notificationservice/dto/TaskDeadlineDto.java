package com.example.notificationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDeadlineDto {

    @NotNull(message = "Task ID cannot be null")
    Long taskId;

    @NotBlank(message = "Task description cannot be blank")
    String description;

    @NotNull(message = "Deadline cannot be null")
    @FutureOrPresent(message = "Deadline must be in the present or future")
    LocalDateTime deadlineDate;

    @Email(message = "User email must be a well-formed email address")
    String userEmail;
}

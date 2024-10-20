package com.example.tasktrackerservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskReadDto {

    Long id;

    String name;

    @JsonProperty("created_at")
    LocalDateTime createdAt;

    String description;
}

package com.example.timetrackerservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskTimeDto {

    Long taskId;

    LocalDateTime startTime;

    LocalDateTime endTime;
}

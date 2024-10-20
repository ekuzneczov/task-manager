package com.example.timetrackerservice.controller;

import com.example.timetrackerservice.dto.TaskTimeDto;
import com.example.timetrackerservice.service.TaskTimeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/task_time")
public class TaskTimeController {

    TaskTimeService taskTimeService;

    @GetMapping("/start/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public TaskTimeDto startTracking(@PathVariable Long taskId) {
        return taskTimeService.startTracking(taskId);
    }

    @GetMapping("/stop/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public TaskTimeDto stopTracking(@PathVariable Long taskId) {
        return taskTimeService.stopTracking(taskId);
    }

    @GetMapping("/duration/{taskId}")
    public Duration getTrackedTime(@PathVariable Long taskId) {
        return taskTimeService.getTrackedTime(taskId);
    }
}

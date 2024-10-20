package com.example.notificationservice.controller;

import com.example.notificationservice.dto.TaskDeadlineDto;
import com.example.notificationservice.service.TaskDeadlineService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/task_deadline")
public class TaskDeadlineDtoController {

    TaskDeadlineService taskDeadlineService;

    @PostMapping()
    public TaskDeadlineDto createOrUpdateDeadline(@Valid @RequestBody TaskDeadlineDto taskDeadlineDto) {
        return taskDeadlineService.createOrUpdateDeadline(taskDeadlineDto);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeadline(@PathVariable Long taskId) {
        taskDeadlineService.deleteDeadline(taskId);
    }

}

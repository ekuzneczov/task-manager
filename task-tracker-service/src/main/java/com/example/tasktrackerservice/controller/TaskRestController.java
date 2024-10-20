package com.example.tasktrackerservice.controller;

import com.example.tasktrackerservice.dto.TaskCreateEditDto;
import com.example.tasktrackerservice.dto.TaskReadDto;
import com.example.tasktrackerservice.exeption.NotFoundException;
import com.example.tasktrackerservice.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/tasks")
public class TaskRestController {

    TaskService taskService;

    @GetMapping("/{taskStateId}")
    public List<TaskReadDto> findAllByTaskStateId(@PathVariable Long taskStateId) {
        return taskService.findAllByTaskStateId(taskStateId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TaskReadDto create(@RequestBody @Validated TaskCreateEditDto taskDto) {
        return taskService.create(taskDto);
    }

    @PatchMapping("/{id}")
    public TaskReadDto update(@PathVariable Long id,
                              @RequestBody @Validated TaskCreateEditDto taskDto) {
        return taskService.update(id, taskDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!taskService.delete(id)) {
            throw new NotFoundException(format("Task with id '%s' not found", id));
        }
    }
}

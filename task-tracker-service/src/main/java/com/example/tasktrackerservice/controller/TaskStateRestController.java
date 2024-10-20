package com.example.tasktrackerservice.controller;

import com.example.tasktrackerservice.dto.TaskStateCreateDto;
import com.example.tasktrackerservice.dto.TaskStateEditDto;
import com.example.tasktrackerservice.dto.TaskStateReadDto;
import com.example.tasktrackerservice.exeption.NotFoundException;
import com.example.tasktrackerservice.service.TaskStateService;
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
@RequestMapping("/api/v1/task_states")
public class TaskStateRestController {

    TaskStateService taskStateService;

    @GetMapping("/{projectId}")
    public List<TaskStateReadDto> findAllByProjectId(@PathVariable Long projectId) {
        return taskStateService.findAllByProjectId(projectId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TaskStateReadDto create(@RequestBody @Validated TaskStateCreateDto taskStateDto) {
        return taskStateService.create(taskStateDto);
    }

    @PatchMapping("/{id}")
    public TaskStateReadDto update(@PathVariable Long id,
                                   @RequestBody @Validated TaskStateEditDto taskStateDto) {
        return taskStateService.update(id, taskStateDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!taskStateService.delete(id)) {
            throw new NotFoundException(format("Task state with id '%s' not found", id));
        }
    }
}

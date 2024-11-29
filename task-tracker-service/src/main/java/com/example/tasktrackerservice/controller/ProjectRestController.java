package com.example.tasktrackerservice.controller;

import com.example.tasktrackerservice.dto.ProjectCreateEditDto;
import com.example.tasktrackerservice.dto.ProjectReadDto;
import com.example.tasktrackerservice.exeption.BadRequestException;
import com.example.tasktrackerservice.exeption.NotFoundException;
import com.example.tasktrackerservice.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/projects")
public class ProjectRestController {

    ProjectService projectService;

    @GetMapping
    public List<ProjectReadDto> findAll() {
        return projectService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ProjectReadDto create(@RequestBody @Validated ProjectCreateEditDto project) {
        try {
            return projectService.create(new ProjectCreateEditDto(project.name()));
        } catch (Exception ex) {
            throw new BadRequestException(format("Project '%s' already exists", project.name()));
        }
    }

    @PatchMapping("/{id}")
    public ProjectReadDto update(@PathVariable Long id,
                                 @Validated @RequestBody ProjectCreateEditDto projectDto) {

        return projectService.update(id, projectDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!projectService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND,
                    String.format("Project with id '%s' not found", id));
        }
    }
}

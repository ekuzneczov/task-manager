package com.example.tasktrackerservice.integration.service;

import com.example.tasktrackerservice.dto.ProjectReadDto;
import com.example.tasktrackerservice.integration.IntegrationTest;
import com.example.tasktrackerservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class ProjectServiceIT extends IntegrationTest {

    private final ProjectService projectService;

    @Test
    void findById() {
        Long projectId = 1L;
        Optional<ProjectReadDto> actualResult = projectService.findById(projectId);
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getName()).isEqualTo("Test my app");
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
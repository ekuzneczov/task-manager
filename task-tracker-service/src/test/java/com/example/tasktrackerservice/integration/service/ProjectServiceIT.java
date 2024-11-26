package com.example.tasktrackerservice.integration.service;

import com.example.tasktrackerservice.dto.ProjectCreateEditDto;
import com.example.tasktrackerservice.dto.ProjectReadDto;
import com.example.tasktrackerservice.integration.IntegrationTest;
import com.example.tasktrackerservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
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
        List<ProjectReadDto> actualResult = projectService.findAll();
        assertThat(actualResult).hasSize(2);
        assertThat(actualResult.get(0).getName()).isEqualTo("Test my app");
        assertThat(actualResult.get(1).getName()).isEqualTo("Deploy another app");
    }

    @Test
    void create() {
        ProjectCreateEditDto createDto = new ProjectCreateEditDto("Created project");

        ProjectReadDto actualResult = projectService.create(createDto);

        assertThat(actualResult.getId()).isNotNull();
        assertThat(actualResult.getName()).isEqualTo(createDto.name());
    }

    @Test
    void update() {
        Long projectToUpdateId = 1L;
        ProjectCreateEditDto updateDto = new ProjectCreateEditDto("Created project");

        Optional<ProjectReadDto> actualResult = projectService.update(projectToUpdateId, updateDto);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getId()).isEqualTo(projectToUpdateId);
        assertThat(actualResult.get().getName()).isEqualTo(updateDto.name());
    }

    @Test
    void delete() {
        Long projectToDeleteId = 1L;

        boolean actualResult = projectService.delete(projectToDeleteId);
        assertThat(actualResult).isTrue();

        Optional<ProjectReadDto> projectById = projectService.findById(projectToDeleteId);
        assertThat(projectById).isEmpty();
    }
}
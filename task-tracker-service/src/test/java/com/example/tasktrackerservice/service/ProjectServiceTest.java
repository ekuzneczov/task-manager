package com.example.tasktrackerservice.service;

import com.example.tasktrackerservice.database.entity.Project;
import com.example.tasktrackerservice.database.repository.ProjectRepository;
import com.example.tasktrackerservice.dto.ProjectCreateEditDto;
import com.example.tasktrackerservice.dto.ProjectReadDto;
import com.example.tasktrackerservice.mapper.ProjectCreateEditMapper;
import com.example.tasktrackerservice.mapper.ProjectReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectCreateEditMapper projectCreateEditMapper;

    @Mock
    private ProjectReadMapper projectReadMapper;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void findById() {
        Long projectId = 1L;
        String projectName = "Test Project";

        Project project = Project.builder()
                .id(projectId)
                .name(projectName)
                .createdAt(LocalDateTime.now())
                .build();

        ProjectReadDto projectReadDto = ProjectReadDto.builder()
                .id(projectId)
                .name(projectName)
                .createdAt(project.getCreatedAt())
                .build();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectReadMapper.map(project)).thenReturn(projectReadDto);

        Optional<ProjectReadDto> actualResult = projectService.findById(projectId);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(projectReadDto);

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectReadMapper, times(1)).map(project);
    }

    @Test
    void findAll() {
        Project projectOne = Project.builder()
                .id(1L)
                .name("Project One")
                .createdAt(LocalDateTime.now())
                .build();
        Project projectTwo = Project.builder()
                .id(2L)
                .name("Project Two")
                .createdAt(LocalDateTime.now())
                .build();
        Project projectThree = Project.builder()
                .id(3L)
                .name("Project Three")
                .createdAt(LocalDateTime.now())
                .build();

        ProjectReadDto dtoOne = ProjectReadDto.builder()
                .id(projectOne.getId())
                .name(projectOne.getName())
                .createdAt(projectOne.getCreatedAt())
                .build();
        ProjectReadDto dtoTwo = ProjectReadDto.builder()
                .id(projectTwo.getId())
                .name(projectTwo.getName())
                .createdAt(projectTwo.getCreatedAt())
                .build();
        ProjectReadDto dtoThree = ProjectReadDto.builder()
                .id(projectThree.getId())
                .name(projectThree.getName())
                .createdAt(projectThree.getCreatedAt())
                .build();

        when(projectRepository.findAll()).thenReturn(List.of(projectOne, projectTwo, projectThree));

        when(projectReadMapper.map(projectOne)).thenReturn(dtoOne);
        when(projectReadMapper.map(projectTwo)).thenReturn(dtoTwo);
        when(projectReadMapper.map(projectThree)).thenReturn(dtoThree);

        List<ProjectReadDto> actualResult = projectService.findAll();

        assertThat(actualResult).hasSize(3);
        assertThat(actualResult).containsExactlyElementsOf(List.of(dtoOne, dtoTwo, dtoThree));

        verify(projectRepository, times(1)).findAll();
        verify(projectReadMapper, times(1)).map(projectOne);
        verify(projectReadMapper, times(1)).map(projectTwo);
        verify(projectReadMapper, times(1)).map(projectThree);
    }

    @Test
    void create() {
        ProjectCreateEditDto projectCreateDto = new ProjectCreateEditDto("Test Project");
        Project project = Project.builder()
                .name(projectCreateDto.name())
                .build();
        Project savedProject = Project.builder()
                .id(1L)
                .name(projectCreateDto.name())
                .build();
        ProjectReadDto projectReadDto = ProjectReadDto.builder()
                .id(savedProject.getId())
                .name(savedProject.getName())
                .createdAt(savedProject.getCreatedAt())
                .build();

        doReturn(project)
                .when(projectCreateEditMapper)
                .map(projectCreateDto);
        doReturn(savedProject)
                .when(projectRepository)
                .save(project);
        doReturn(projectReadDto)
                .when(projectReadMapper)
                .map(savedProject);

        ProjectReadDto actualResult = projectService.create(projectCreateDto);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(projectReadDto);

        verify(projectCreateEditMapper, times(1)).map(projectCreateDto);
        verify(projectRepository, times(1)).save(project);
        verify(projectReadMapper, times(1)).map(savedProject);
    }

    @Test
    void update() {
        Long projectId = 1L;
        ProjectCreateEditDto updateDto = new ProjectCreateEditDto("Updated Project");
        Project existingProject = Project.builder()
                .id(projectId)
                .name("Existing project")
                .createdAt(LocalDateTime.now())
                .build();
        Project updatedProject = Project.builder()
                .id(projectId)
                .name(updateDto.name())
                .createdAt(existingProject.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        ProjectReadDto readDto = ProjectReadDto.builder()
                .id(projectId)
                .name(updateDto.name())
                .createdAt(updatedProject.getCreatedAt())
                .updatedAt(updatedProject.getUpdatedAt())
                .build();

        doReturn(Optional.of(existingProject))
                .when(projectRepository)
                .findById(projectId);
        doReturn(updatedProject)
                .when(projectCreateEditMapper)
                .map(updateDto, existingProject);
        doReturn(updatedProject)
                .when(projectRepository)
                .saveAndFlush(updatedProject);
        doReturn(readDto)
                .when(projectReadMapper)
                .map(updatedProject);

        Optional<ProjectReadDto> actualResult = projectService.update(projectId, updateDto);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(readDto);

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectCreateEditMapper, times(1)).map(updateDto, existingProject);
        verify(projectRepository, times(1)).saveAndFlush(updatedProject);
        verify(projectReadMapper, times(1)).map(updatedProject);
    }

    @Test
    void delete() {
        Long projectId = 1L;
        Project existingProject = Project.builder()
                .id(projectId)
                .name("Existing project")
                .createdAt(LocalDateTime.now())
                .build();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));

        boolean actualResult = projectService.delete(projectId);

        assertThat(actualResult).isTrue();

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).delete(existingProject);
        verify(projectRepository, times(1)).flush();
    }

    @Test
    void deleteNotFound() {
        Long projectId = 1L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        boolean result = projectService.delete(projectId);

        assertThat(result).isFalse();

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, never()).delete(any());
        verify(projectRepository, never()).flush();
    }
}
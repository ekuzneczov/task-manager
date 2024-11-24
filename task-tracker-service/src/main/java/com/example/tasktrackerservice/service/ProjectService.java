package com.example.tasktrackerservice.service;

import com.example.tasktrackerservice.database.entity.Project;
import com.example.tasktrackerservice.database.repository.ProjectRepository;
import com.example.tasktrackerservice.dto.ProjectCreateEditDto;
import com.example.tasktrackerservice.dto.ProjectReadDto;
import com.example.tasktrackerservice.mapper.ProjectCreateEditMapper;
import com.example.tasktrackerservice.mapper.ProjectReadMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ProjectService {

    ProjectRepository projectRepository;
    ProjectCreateEditMapper projectCreateEditMapper;
    ProjectReadMapper projectReadMapper;

    public Optional<ProjectReadDto> findById(Long id) {
        return projectRepository.findById(id)
                .map(projectReadMapper::map);
    }

    public List<ProjectReadDto> findAll() {
        return projectRepository.findAll().stream()
                .map(projectReadMapper::map)
                .toList();
    }

    @Transactional
    public ProjectReadDto create(ProjectCreateEditDto projectDto) {
        Project project = projectCreateEditMapper.map(projectDto);
//        project.setUpdatedAt(null);
        return projectReadMapper.map(
                projectRepository.save(project)
        );
    }


    @Transactional
    public Optional<ProjectReadDto> update(Long id, ProjectCreateEditDto projectDto) {
        return projectRepository.findById(id)
                .map(entity -> projectCreateEditMapper.map(projectDto, entity))
                .map(projectRepository::saveAndFlush)
                .map(projectReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return projectRepository.findById(id)
                .map(entity -> {
                    projectRepository.delete(entity);
                    projectRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}

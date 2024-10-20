package com.example.tasktrackerservice.mapper;

import com.example.tasktrackerservice.database.entity.Project;
import com.example.tasktrackerservice.dto.ProjectReadDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectReadMapper implements Mapper<Project, ProjectReadDto> {

    @Override
    public ProjectReadDto map(Project object) {
        return ProjectReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .createdAt(object.getCreatedAt())
                .updatedAt(object.getUpdatedAt())
                .build();
    }
}

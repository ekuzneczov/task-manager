package com.example.tasktrackerservice.mapper;

import com.example.tasktrackerservice.database.entity.Project;
import com.example.tasktrackerservice.dto.ProjectCreateEditDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProjectCreateEditMapper implements Mapper<ProjectCreateEditDto, Project> {


    @Override
    public Project map(ProjectCreateEditDto object) {
        Project project = new Project();
        copy(object, project);
        return project;
    }

    @Override
    public Project map(ProjectCreateEditDto fromObject, Project toObject) {
        copy(fromObject, toObject);
        toObject.setUpdatedAt(LocalDateTime.now());
        return toObject;
    }

    private static void copy(ProjectCreateEditDto object, Project project) {
        project.setName(object.name());
    }
}

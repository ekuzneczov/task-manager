package com.example.tasktrackerservice.mapper;

import com.example.tasktrackerservice.database.entity.Project;
import com.example.tasktrackerservice.database.entity.TaskState;
import com.example.tasktrackerservice.database.repository.ProjectRepository;
import com.example.tasktrackerservice.database.repository.TaskStateRepository;
import com.example.tasktrackerservice.dto.TaskStateCreateDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskStateCreateMapper implements Mapper<TaskStateCreateDto, TaskState> {

    ProjectRepository projectRepository;
    TaskStateRepository taskStateRepository;

    @Override
    public TaskState map(TaskStateCreateDto object) {

        Optional<TaskState> lastTaskState = getLastTaskState();

        TaskState taskState = TaskState.builder()
                .name(object.name())
                .project(getProject(object.projectId()))
                .leftTaskState(lastTaskState.orElse(null))
                .rightTaskState(null)
                .build();


        lastTaskState.ifPresent(entity -> {
            entity.setRightTaskState(taskState);
            taskStateRepository.save(entity);
        });

        return taskState;
    }

    private Project getProject(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    private Optional<TaskState> getLastTaskState() {
        return taskStateRepository.findAll().stream()
                .max(Comparator.comparing(TaskState::getId));
    }
}

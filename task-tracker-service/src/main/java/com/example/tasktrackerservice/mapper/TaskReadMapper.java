package com.example.tasktrackerservice.mapper;

import com.example.tasktrackerservice.database.entity.Task;
import com.example.tasktrackerservice.dto.TaskReadDto;
import org.springframework.stereotype.Component;

@Component
public class TaskReadMapper implements Mapper<Task, TaskReadDto> {

    @Override
    public TaskReadDto map(Task object) {
        return TaskReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .createdAt(object.getCreatedAt())
                .description(object.getDescription())
                .build();
    }
}

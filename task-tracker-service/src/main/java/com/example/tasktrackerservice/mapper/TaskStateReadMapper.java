package com.example.tasktrackerservice.mapper;

import com.example.tasktrackerservice.database.entity.TaskState;
import com.example.tasktrackerservice.dto.TaskStateReadDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskStateReadMapper implements Mapper<TaskState, TaskStateReadDto> {

    TaskReadMapper taskReadMapper;

    @Override
    public TaskStateReadDto map(TaskState object) {
        return TaskStateReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .createdAt(object.getCreatedAt())
                .leftTaskStateId(object.getLeftTaskState().map(TaskState::getId).orElse(null))
                .rightTaskStateId(object.getRightTaskState().map(TaskState::getId).orElse(null))
                .tasks(
                        object
                                .getTasks()
                                .stream()
                                .map(taskReadMapper::map)
                                .collect(Collectors.toList())
                )
                .build();
    }

}

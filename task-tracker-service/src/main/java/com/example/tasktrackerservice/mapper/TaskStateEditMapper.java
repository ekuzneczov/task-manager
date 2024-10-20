package com.example.tasktrackerservice.mapper;

import com.example.tasktrackerservice.database.entity.TaskState;
import com.example.tasktrackerservice.database.repository.TaskStateRepository;
import com.example.tasktrackerservice.dto.TaskStateEditDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskStateEditMapper implements Mapper<TaskStateEditDto, TaskState> {

    TaskStateRepository taskStateRepository;

    @Override
    public TaskState map(TaskStateEditDto fromObject, TaskState toObject) {
        toObject.setName(fromObject.name());
        toObject.setLeftTaskState(getTaskState(fromObject.leftTaskStateId()));
        toObject.setRightTaskState(getTaskState(fromObject.rightTaskStateId()));
        return toObject;
    }

    private TaskState getTaskState(Long id) {
        return taskStateRepository.findById(id).orElse(null);
    }
}

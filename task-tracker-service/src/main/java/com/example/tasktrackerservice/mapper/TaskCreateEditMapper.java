package com.example.tasktrackerservice.mapper;

import com.example.tasktrackerservice.database.entity.Task;
import com.example.tasktrackerservice.database.entity.TaskState;
import com.example.tasktrackerservice.database.repository.TaskStateRepository;
import com.example.tasktrackerservice.dto.TaskCreateEditDto;
import com.example.tasktrackerservice.exeption.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskCreateEditMapper implements Mapper<TaskCreateEditDto, Task> {

    TaskStateRepository taskStateRepository;

    @Override
    public Task map(TaskCreateEditDto object) {
        Task task = new Task();
        copy(object, task);
        return task;
    }

    @Override
    public Task map(TaskCreateEditDto fromObject, Task toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(TaskCreateEditDto object, Task task) {
        task.setTaskState(getTaskState(object.taskStateId()));
        task.setName(object.name());
        task.setDescription(object.description());
    }

    private TaskState getTaskState(Long taskStateId) {
        return taskStateRepository.findById(taskStateId)
                .orElseThrow(() -> new NotFoundException(
                        format("Task state with id = %s not found", taskStateId)
                ));
    }

}

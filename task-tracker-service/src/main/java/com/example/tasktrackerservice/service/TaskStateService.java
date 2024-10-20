package com.example.tasktrackerservice.service;

import com.example.tasktrackerservice.database.entity.TaskState;
import com.example.tasktrackerservice.database.repository.TaskStateRepository;
import com.example.tasktrackerservice.dto.TaskStateCreateDto;
import com.example.tasktrackerservice.dto.TaskStateEditDto;
import com.example.tasktrackerservice.dto.TaskStateReadDto;
import com.example.tasktrackerservice.mapper.TaskStateCreateMapper;
import com.example.tasktrackerservice.mapper.TaskStateEditMapper;
import com.example.tasktrackerservice.mapper.TaskStateReadMapper;
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
public class TaskStateService {

    TaskStateRepository taskStateRepository;
    TaskStateReadMapper taskStateReadMapper;
    TaskStateCreateMapper taskStateCreateMapper;
    TaskStateEditMapper taskStateEditMapper;

    public List<TaskStateReadDto> findAllByProjectId(Long projectId) {
        return taskStateRepository.findByProjectId(projectId).stream()
                .map(taskStateReadMapper::map)
                .toList();
    }

    @Transactional
    public TaskStateReadDto create(TaskStateCreateDto taskStateDto) {
        TaskState savedTaskState = taskStateRepository.save(taskStateCreateMapper.map(taskStateDto));
        return taskStateReadMapper.map(savedTaskState);
    }

    @Transactional
    public Optional<TaskStateReadDto> update(Long id, TaskStateEditDto taskStateDto) {
        return taskStateRepository.findById(id)
                .map(entity -> taskStateEditMapper.map(taskStateDto, entity))
                .map(taskStateRepository::save)
                .map(taskStateReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return taskStateRepository.findById(id)
                .map(entity -> {
                    entity.getLeftTaskState().ifPresent(leftTaskState -> {
                        leftTaskState.setRightTaskState(null);
                        taskStateRepository.save(leftTaskState);
                    });
                    taskStateRepository.delete(entity);
                    taskStateRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}

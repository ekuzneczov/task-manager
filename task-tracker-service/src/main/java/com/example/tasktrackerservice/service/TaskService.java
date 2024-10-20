package com.example.tasktrackerservice.service;

import com.example.tasktrackerservice.database.repository.TaskRepository;
import com.example.tasktrackerservice.dto.TaskCreateEditDto;
import com.example.tasktrackerservice.dto.TaskReadDto;
import com.example.tasktrackerservice.mapper.TaskCreateEditMapper;
import com.example.tasktrackerservice.mapper.TaskReadMapper;
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
public class TaskService {

    TaskRepository taskRepository;
    TaskReadMapper taskReadMapper;
    TaskCreateEditMapper taskCreateEditMapper;

    public List<TaskReadDto> findAllByTaskStateId(Long projectId) {
        return taskRepository.findAllByTaskStateId(projectId)
                .stream().map(taskReadMapper::map).toList();
    }

    @Transactional
    public TaskReadDto create(TaskCreateEditDto taskDto) {
        return taskReadMapper.map(taskRepository.save(taskCreateEditMapper.map(taskDto)));
    }


    @Transactional
    public Optional<TaskReadDto> update(Long id, TaskCreateEditDto taskDto) {
        return taskRepository.findById(id)
                .map(entity -> taskCreateEditMapper.map(taskDto, entity))
                .map(taskRepository::save)
                .map(taskReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return taskRepository.findById(id)
                .map(entity -> {
                    taskRepository.delete(entity);
                    taskRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}

package com.example.notificationservice.service;

import com.example.notificationservice.database.entity.TaskDeadline;
import com.example.notificationservice.database.repository.TaskDeadlineRepository;
import com.example.notificationservice.dto.TaskDeadlineDto;
import com.example.notificationservice.mapper.TaskDeadlineMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TaskDeadlineService {

    TaskDeadlineRepository taskDeadlineRepository;
    TaskDeadlineMapper mapper;

    @Transactional
    public TaskDeadlineDto createOrUpdateDeadline(TaskDeadlineDto taskDeadlineDto) {
        return taskDeadlineRepository.findByTaskId(taskDeadlineDto.getTaskId())
                .map(existingDeadline -> {
                    existingDeadline.setDeadlineDate(taskDeadlineDto.getDeadlineDate());
                    return mapper.toTaskDeadlineDto(taskDeadlineRepository.save(existingDeadline));
                })
                .orElseGet(() -> {
                    TaskDeadline newDeadline = mapper.toTaskDeadline(taskDeadlineDto);
                    return mapper.toTaskDeadlineDto(taskDeadlineRepository.save(newDeadline));
                });
    }

    @Transactional
    public void deleteDeadline(Long taskId) {
        taskDeadlineRepository.findByTaskId(taskId)
                .ifPresent(taskDeadlineRepository::delete);
    }
}

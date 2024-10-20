package com.example.timetrackerservice.service;

import com.example.timetrackerservice.database.entity.TaskTime;
import com.example.timetrackerservice.database.repository.TaskTimeRepository;
import com.example.timetrackerservice.dto.TaskTimeDto;
import com.example.timetrackerservice.exeption.NotFoundException;
import com.example.timetrackerservice.mapper.TaskTimeDtoMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TaskTimeService {

    TaskTimeRepository taskTimeRepository;
    TaskTimeDtoMapper mapper;

    @Transactional
    public TaskTimeDto startTracking(Long taskId) {

        return mapper.toTaskTimeDto(
                taskTimeRepository.save(
                        TaskTime.builder()
                                .taskId(taskId)
                                .startTime(LocalDateTime.now())
                                .build()
                )
        );
    }

    @Transactional
    public TaskTimeDto stopTracking(Long taskId) {

        return taskTimeRepository.findByTaskId(taskId).map(taskTime -> {
            taskTime.setEndTime(LocalDateTime.now());
            return mapper.toTaskTimeDto(taskTimeRepository.save(taskTime));
        }).orElseThrow(() -> new NotFoundException("TaskTime not found with taskId " + taskId));
    }

    public Duration getTrackedTime(Long taskId) {

        return taskTimeRepository.findByTaskId(taskId)
                .map(taskTime -> Duration.between(taskTime.getStartTime(), taskTime.getEndTime()))
                .orElseThrow(() -> new NotFoundException("TaskTime not found with taskId " + taskId));
    }
}

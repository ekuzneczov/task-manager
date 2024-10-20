package com.example.notificationservice.mapper;

import com.example.notificationservice.database.entity.TaskDeadline;
import com.example.notificationservice.dto.TaskDeadlineDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskDeadlineMapper {

    TaskDeadline toTaskDeadline(TaskDeadlineDto taskDeadlineDto);

    TaskDeadlineDto toTaskDeadlineDto(TaskDeadline taskDeadline);
}

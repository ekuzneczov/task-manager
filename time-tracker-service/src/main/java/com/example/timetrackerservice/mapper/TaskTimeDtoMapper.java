package com.example.timetrackerservice.mapper;


import com.example.timetrackerservice.database.entity.TaskTime;
import com.example.timetrackerservice.dto.TaskTimeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskTimeDtoMapper {

    TaskTime toTaskTime(TaskTimeDto taskTimeDto);

    TaskTimeDto toTaskTimeDto(TaskTime taskTime);
}

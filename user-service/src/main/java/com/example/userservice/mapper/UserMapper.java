package com.example.userservice.mapper;

import com.example.userservice.database.entity.User;
import com.example.userservice.dto.UserCreateUpdateDto;
import com.example.userservice.dto.UserReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User fromUserReadDtoToUser(UserReadDto userReadDto);

    UserReadDto fromUserToUserReadDto(User user);

}

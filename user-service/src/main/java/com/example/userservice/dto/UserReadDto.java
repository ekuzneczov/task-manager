package com.example.userservice.dto;

import com.example.userservice.database.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public final class UserReadDto {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private Collection<Role> roles;
}

package com.example.userservice.controller;

import com.example.userservice.dto.UserCreateUpdateDto;
import com.example.userservice.dto.UserReadDto;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public ResponseEntity<UserReadDto> update(@RequestBody @Valid UserCreateUpdateDto createUpdateDto) {
        return userService.createOrUpdate(createUpdateDto)
                .map(userReadDto -> ResponseEntity.status(HttpStatus.CREATED).body(userReadDto))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or principal.username == #username")
    public void delete(@PathVariable String username) {
        userService.delete(username);
    }
}

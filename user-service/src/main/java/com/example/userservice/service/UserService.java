package com.example.userservice.service;

import com.example.userservice.database.entity.Role;
import com.example.userservice.database.entity.User;
import com.example.userservice.database.repository.RoleRepository;
import com.example.userservice.database.repository.UserRepository;
import com.example.userservice.dto.UserCreateUpdateDto;
import com.example.userservice.dto.UserReadDto;
import com.example.userservice.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<UserReadDto> findByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::fromUserToUserReadDto);
    }

    @Transactional
    public Optional<UserReadDto> createOrUpdate(UserCreateUpdateDto request) {
        List<Role> roles = request.roles()
                .stream()
                .map(roleRepository::findByName)
                .map(Optional::orElseThrow)
                .toList();

        return userRepository.findByUsername(request.username())
                .map(existingUser -> {
                    existingUser.setUsername(request.username());
                    existingUser.setFirstname(request.firstname());
                    existingUser.setLastname(request.lastname());
                    existingUser.setEmail(request.email());
                    existingUser.setPassword(bCryptPasswordEncoder.encode(request.password()));
                    existingUser.setRoles(roles);
                    return userMapper.fromUserToUserReadDto(userRepository.save(existingUser));
                })
                .or(() -> Optional.of(
                        userMapper.fromUserToUserReadDto(
                                userRepository.save(
                                        User.builder()
                                                .username(request.username())
                                                .firstname(request.firstname())
                                                .lastname(request.lastname())
                                                .email(request.email())
                                                .password(bCryptPasswordEncoder.encode(request.password()))
                                                .roles(roles)
                                                .build()
                                )
                        )
                ));
    }


    @Transactional
    public boolean delete(String username) {
        return userRepository.findByUsername(username)
                .map(entity -> {
                    userRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(username)
                .map(userMapper::fromUserReadDtoToUser)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

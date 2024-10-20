package com.example.userservice.service;

import com.example.userservice.dto.JwtAuthenticationDto;
import com.example.userservice.dto.SignInDto;
import com.example.userservice.dto.UserCreateUpdateDto;
import com.example.userservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationDto signUp(UserCreateUpdateDto request) {
        return userService.createOrUpdate(request)
                .map(userReadDto -> {
                    String token = jwtService.generateToken(userMapper.fromUserReadDtoToUser(userReadDto));
                    return new JwtAuthenticationDto(token);
                })
                .orElseThrow(() -> new RuntimeException("Username not saved"));
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationDto signIn(SignInDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        ));

        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.username());

        String jwt = jwtService.generateToken(user);

        return new JwtAuthenticationDto(jwt);
    }
}

package com.example.userservice;

import com.example.userservice.database.entity.Role;
import com.example.userservice.database.entity.RoleEnum;
import com.example.userservice.database.entity.User;
import com.example.userservice.database.repository.RoleRepository;
import com.example.userservice.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminSetup {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        User adminUser = User.builder()
                .username("admin")
                .firstname("Alex")
                .lastname("Sokolov")
                .email("test@mail.com")
                .roles(List.of(getRole(RoleEnum.ROLE_ADMIN.name())))
                .password(bCryptPasswordEncoder.encode("admin"))
                .build();

        if (!userRepository.existsByUsername(adminUser.getUsername())) {
            userRepository.save(adminUser);
        }
    }

    private Role getRole(String name) {
        return roleRepository.findByName(name)
                .orElseThrow();
    }
}

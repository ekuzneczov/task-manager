package com.example.tasktrackerservice.database.repository;

import com.example.tasktrackerservice.database.entity.Project;
import com.example.tasktrackerservice.integration.IntegrationTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@RequiredArgsConstructor
class ProjectRepositoryIT extends IntegrationTest {

    private final ProjectRepository projectRepository;

    @Test
    void findByName_shouldReturnProject_whenNameExists() {
        String projectName = "Deploy another app";

        Optional<Project> foundProject = projectRepository.findByName(projectName);

        assertThat(foundProject).isPresent();
        assertThat(foundProject.get().getName()).isEqualTo(projectName);
    }

    @Test
    void findByName_shouldReturnEmptyOptional_whenNameDoesNotExist() {
        Optional<Project> foundProject = projectRepository.findByName("Nonexistent Project");

        assertThat(foundProject).isEmpty();
    }
}
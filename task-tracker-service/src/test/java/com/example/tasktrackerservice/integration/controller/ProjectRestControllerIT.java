package com.example.tasktrackerservice.integration.controller;

import com.example.tasktrackerservice.dto.ProjectCreateEditDto;
import com.example.tasktrackerservice.integration.IntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class ProjectRestControllerIT extends IntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Test
    void findAll() throws Exception {
        mockMvc
                .perform(get("/api/v1/projects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test my app")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Deploy another app")));
    }

    @Test
    void create() throws Exception {
        ProjectCreateEditDto projectToCreate = new ProjectCreateEditDto("Project to create");

        mockMvc.perform(post("/api/v1/projects")
                        .content(objectMapper.writeValueAsString(projectToCreate))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is(projectToCreate.name())));
    }

    @Test
    void update() throws Exception {
        Long projectId = 1L;
        ProjectCreateEditDto projectToUpdate = new ProjectCreateEditDto("Updated project");

        mockMvc.perform(patch("/api/v1/projects/{projectId}", projectId)
                        .content(objectMapper.writeValueAsString(projectToUpdate))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(projectToUpdate.name())));
    }

    @Test
    void update_ShouldReturnNotFound_WhenProjectDoesNotExist() throws Exception {
        Long projectId = 10L;
        ProjectCreateEditDto updateDto = new ProjectCreateEditDto("Updated Project");

        mockMvc.perform(patch("/api/v1/projects/{id}", projectId)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        Long projectId = 1L;
        mockMvc.perform(delete("/api/v1/projects/{projectId}", projectId))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_ShouldReturnNotFound_WhenProjectDoesNotExist() throws Exception {
        Long projectId = 20L;
        mockMvc.perform(delete("/api/v1/projects/{projectId}", projectId))
                .andExpect(status().isNotFound());
    }
}
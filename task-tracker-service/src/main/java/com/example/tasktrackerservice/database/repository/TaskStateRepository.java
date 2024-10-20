package com.example.tasktrackerservice.database.repository;

import com.example.tasktrackerservice.database.entity.TaskState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskStateRepository extends JpaRepository<TaskState, Long> {

    List<TaskState> findByProjectId(Long projectId);
}

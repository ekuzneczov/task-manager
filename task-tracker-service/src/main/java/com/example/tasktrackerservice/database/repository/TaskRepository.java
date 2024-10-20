package com.example.tasktrackerservice.database.repository;

import com.example.tasktrackerservice.database.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByTaskStateId(Long taskStateId);

}

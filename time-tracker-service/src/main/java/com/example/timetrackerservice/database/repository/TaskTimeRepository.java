package com.example.timetrackerservice.database.repository;

import com.example.timetrackerservice.database.entity.TaskTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskTimeRepository extends JpaRepository<TaskTime, Long> {

    Optional<TaskTime> findByTaskId(Long taskId);
}

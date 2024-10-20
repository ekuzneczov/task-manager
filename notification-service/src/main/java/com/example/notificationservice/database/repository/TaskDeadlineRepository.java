package com.example.notificationservice.database.repository;

import com.example.notificationservice.database.entity.TaskDeadline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskDeadlineRepository extends JpaRepository<TaskDeadline, Long> {

    Optional<TaskDeadline> findByTaskId(Long taskId);

    List<TaskDeadline> findAllByDeadlineDateBeforeAndIsNotifiedFalse(LocalDateTime now);
}

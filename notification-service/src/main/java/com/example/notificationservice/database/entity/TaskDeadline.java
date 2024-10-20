package com.example.notificationservice.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class TaskDeadline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    Long taskId;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    LocalDateTime deadlineDate;

    @Column(nullable = false)
    String userEmail;

    @Column(nullable = false)
    boolean isNotified = false;
}

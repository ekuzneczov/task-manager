package com.example.notificationservice.service;

import com.example.notificationservice.database.entity.TaskDeadline;
import com.example.notificationservice.database.repository.TaskDeadlineRepository;
import com.example.notificationservice.dto.EmailMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReminderService {

    @Value("${rabbitmq.exchange}")
    private String emailExchange;

    @Value("${rabbitmq.routing-key}")
    private String emailExchangeKey;

    private final TaskDeadlineRepository taskDeadlineRepository;
    private final RabbitMQProducerService rabbitService;
    private final ObjectMapper objectMapper;

    //    @Scheduled(fixedRateString = "${task.deadline-check-period}", timeUnit = TimeUnit.SECONDS)
    public void checkDeadlines() {

        List<TaskDeadline> overdueDeadlines = taskDeadlineRepository.findAllByDeadlineDateBeforeAndIsNotifiedFalse(LocalDateTime.now());

        overdueDeadlines.forEach(deadline -> {
            EmailMessage emailMessage = new EmailMessage(
                    deadline.getUserEmail(),
                    "You must complete the task before " + deadline.getDeadlineDate().toString(),
                    deadline.getDescription()
            );

            try {
                String messageJson = objectMapper.writeValueAsString(emailMessage);
                rabbitService.sendMessage(messageJson, emailExchange, emailExchangeKey);

                // Обновляем статус, чтобы пометить дедлайн как уведомленный
                deadline.setNotified(true);
                taskDeadlineRepository.save(deadline);

            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        });
    }
}

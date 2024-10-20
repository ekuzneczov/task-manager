package com.example.emailservice.service;

import com.example.emailservice.dto.EmailMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableRabbit
public class EmailService {

    JavaMailSender emailSender;
    ObjectMapper objectMapper;

    @RabbitListener(queues = "emailQueue")
    public void processMyQueue(String message) {

        try {
            EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
            sendSimpleEmail(
                    emailMessage.toAddress(),
                    emailMessage.subject(),
                    emailMessage.message()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    public void sendSimpleEmail(String toAddress, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        emailSender.send(simpleMailMessage);
    }
}

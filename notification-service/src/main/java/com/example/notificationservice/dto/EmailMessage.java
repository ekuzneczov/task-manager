package com.example.notificationservice.dto;

public record EmailMessage(String toAddress,
                           String subject,
                           String message) {
}

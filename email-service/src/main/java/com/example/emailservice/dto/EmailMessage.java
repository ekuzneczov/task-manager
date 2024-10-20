package com.example.emailservice.dto;

public record EmailMessage(String toAddress,
                           String subject,
                           String message) {
}

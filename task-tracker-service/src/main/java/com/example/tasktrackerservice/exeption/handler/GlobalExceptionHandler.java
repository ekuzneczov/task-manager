package com.example.tasktrackerservice.exeption.handler;

import com.example.tasktrackerservice.exeption.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Order
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(final WebRequest request, final Exception exception) {
        log.error("Exception: ", exception);

        HttpStatus statusCode = (exception instanceof ResponseStatusException)
                ? HttpStatus.valueOf(((ResponseStatusException) exception).getStatusCode().value())
                : HttpStatus.INTERNAL_SERVER_ERROR;

        ApiError apiError = ApiError.construct(statusCode, exception, request);

        return new ResponseEntity<>(apiError, statusCode);
    }
}
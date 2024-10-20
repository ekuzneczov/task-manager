package com.example.tasktrackerservice.exeption;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

@Setter
@Getter
public class ApiError {
    private HttpStatus status;
    private String message;
    private String details;

    public ApiError(HttpStatus status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public static ApiError construct(HttpStatus status, Exception ex, WebRequest request) {
        return new ApiError(status, ex.getMessage(), request.getDescription(false));
    }

}

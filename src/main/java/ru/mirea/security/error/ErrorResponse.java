package ru.mirea.security.error;

import java.time.LocalDateTime;

public class ErrorResponse {
    public String message;
    public int status;
    public LocalDateTime timestamp = LocalDateTime.now();
    public String path;

    public ErrorResponse(String message, int status, String path) {
        this.message = message;
        this.status = status;
        this.path = path;
    }
}
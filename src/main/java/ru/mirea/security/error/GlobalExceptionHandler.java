package ru.mirea.security.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String msg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return buildResponse(msg, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(String message, HttpStatus status, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(message, status.value(), request.getRequestURI());
        return new ResponseEntity<>(response, status);
    }
}
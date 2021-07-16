package com.devsuperior.movieflix.error;

import com.devsuperior.movieflix.service.exception.ResourceExistsException;
import com.devsuperior.movieflix.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        String message = "Resource not found";
        return getStandardErrorResponseEntity(request, notFound, message, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus badRequest = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationError validationError = new ValidationError();
        validationError.setStatus(badRequest.value());
        validationError.setTimestamp(LocalDateTime.now());
        validationError.setError("Validation exception");
        validationError.setMessage(ex.getMessage());
        validationError.setPath(request.getRequestURI());

        ex.getBindingResult().getFieldErrors().forEach(field -> {
            validationError.addError(field.getField(), field.getDefaultMessage());
        });

        return ResponseEntity.status(badRequest).body(validationError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        HttpStatus badRequest = HttpStatus.UNPROCESSABLE_ENTITY;
        String message = "Error type";
        return getStandardErrorResponseEntity(request, badRequest, message, ex);
    }

    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<StandardError> validation(ResourceExistsException ex, HttpServletRequest request) {
        HttpStatus badRequest = HttpStatus.CONFLICT;
        String message = "Review exist";
        return getStandardErrorResponseEntity(request, badRequest, message, ex);
    }

    private ResponseEntity<StandardError> getStandardErrorResponseEntity(HttpServletRequest request, HttpStatus badRequest, String message, RuntimeException ex) {
        StandardError standardError = new StandardError();
        standardError.setStatus(badRequest.value());
        standardError.setTimestamp(LocalDateTime.now());
        standardError.setError(message);
        standardError.setMessage(ex.getMessage());
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(badRequest).body(standardError);
    }

}

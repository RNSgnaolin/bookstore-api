package br.com.bookstore.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    
    private final ExceptionMapper mapper;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponse response = new ErrorResponse(
            status.value(),
            request.getRequestURI(),
            mapper.handleException(ex)
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse response = new ErrorResponse(
            status.value(),
            request.getRequestURI(),
            mapper.handleException(ex)
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponse response = new ErrorResponse(
            status.value(),
            request.getRequestURI(),
            mapper.handleException(ex)
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<ErrorResponse> handleJpaSystem(JpaSystemException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponse response = new ErrorResponse(
            status.value(),
            request.getRequestURI(),
            mapper.handleException(ex)
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse response = new ErrorResponse(
            status.value(),
            request.getRequestURI(),
            mapper.handleException(ex)
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBind(BindException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse response = new ErrorResponse(
            status.value(),
            request.getRequestURI(),
            mapper.handleException(ex)
        );

        return ResponseEntity.status(status).body(response);
    }
}

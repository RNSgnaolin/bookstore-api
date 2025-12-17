package br.com.bookstore.exceptions.handler;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.bookstore.exceptions.domain.EntityFieldNotFoundException;
import br.com.bookstore.exceptions.mapper.ErrorResponse;
import br.com.bookstore.exceptions.mapper.ExceptionMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    
    private final ExceptionMapper mapper;

    @ExceptionHandler(EntityFieldNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityFieldNotFound(EntityFieldNotFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        return buildResponse(status, mapper.handleException(ex), request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        return buildResponse(status, mapper.handleException(ex), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return buildResponse(status, mapper.handleException(ex), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;

        return buildResponse(status, mapper.handleException(ex), request);
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<ErrorResponse> handleJpaSystem(JpaSystemException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;

        return buildResponse(status, mapper.handleException(ex), request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return buildResponse(status, mapper.handleException(ex), request);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBind(BindException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return buildResponse(status, mapper.handleException(ex), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptionFallback(Exception ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return buildResponse(status, mapper.handleException(ex), request);
    }
    
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, Map<String, String> errors, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
            status.value(),
            request.getRequestURI(),
            errors
        );

        return ResponseEntity.status(status).body(response);
    }
}

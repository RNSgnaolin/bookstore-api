package br.com.bookstore.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    
    public ErrorResponse(int status, String path, Map<String, String> errors) {
        this.status = status;
        this.path = path;
        this.timestamp = Instant.now();
        this.errors = errors;
    }

    public ErrorResponse(int status, String path) {
        this.status = status;
        this.path = path;
        this.timestamp = Instant.now();
        this.errors = new HashMap<>();
    }

    private final int status;
    private final String path;
    private final Instant timestamp;
    private final Map<String, String> errors;
}

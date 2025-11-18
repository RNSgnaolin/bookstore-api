package br.com.bookstore.exceptions;

import java.time.Instant;

public record ErrorResponse(String message, Instant timestamp) {
    public ErrorResponse() {
        this(null, Instant.now());
    }

    public ErrorResponse(String message) {
        this(message, Instant.now());
    }
}

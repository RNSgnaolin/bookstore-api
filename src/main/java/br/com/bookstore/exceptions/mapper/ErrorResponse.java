package br.com.bookstore.exceptions.mapper;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/*
    Classe usada para retornar no corpo de um ResponseEntity para que erros sejam legíveis
*/
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    
    public ErrorResponse(int status, String path, Map<String, String> errors) {
        this.status = status;
        this.path = path;
        this.timestamp = Instant.now();
        this.errors = errors;
    }

    @Schema(example = "409")
    private final int status;
    @Schema(example = "/books")
    private final String path;
    @Schema(example = "2025-02-14T18:32:45.123Z")
    private final Instant timestamp;
    @Schema(example = "{ \"title\": \"Título já existente para o autor indicado\", \"pageCount\": \"O número de páginas deve ser maior que zero\" }")
    private final Map<String, String> errors;
}
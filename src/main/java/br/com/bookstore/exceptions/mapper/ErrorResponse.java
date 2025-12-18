package br.com.bookstore.exceptions.mapper;

import java.time.Instant;
import java.util.Map;

import org.slf4j.MDC;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.bookstore.infra.TraceIdFilter;
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
        this.traceId = MDC.get(TraceIdFilter.TRACE_ID);
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
    @Schema(description = "Identificação do HTTP Request para logging", example = "7f3a2c1e-9b4d-4c6a-a0f2-8a6d5e3c9b21")
    private final String traceId;
}
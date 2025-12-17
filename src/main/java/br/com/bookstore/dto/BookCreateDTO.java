package br.com.bookstore.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookCreateDTO(
    @NotBlank(message = "Título não pode estar vazio") 
    @Schema(example = "A Game of Thrones")
    String title, 
    @NotNull(message = "Preço inválido") 
    @Positive(message = "Preço deve ser maior que zero")
    @Schema(example = "59.99")
    BigDecimal price, 
    @NotNull(message = "ID do Autor não pode estar vazio") 
    @Schema(example = "1")
    Long authorId, 
    @Positive(message = "Número de páginas deve ser maior que zero")
    @Schema(example = "720")
    int pageCount) {
}

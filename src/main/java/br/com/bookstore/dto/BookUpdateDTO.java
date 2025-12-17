package br.com.bookstore.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record BookUpdateDTO(
    @Schema(example = "A Clash of Kings")
    String title, 
    @Positive(message = "Preço deve ser maior que zero")
    @Schema(example = "72.99")
    BigDecimal price, 
    @Schema(example = "1")
    Long authorId, 
    @Positive(message = "Preço deve ser maior que zero")
    @Schema(example = "761")
    Integer pageCount) {
    
}
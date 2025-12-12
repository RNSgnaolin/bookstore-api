package br.com.bookstore.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookUpdateDTO(
    @Schema(example = "A Clash of Kings")
    String title, 
    @Schema(example = "72.99")
    BigDecimal price, 
    @Schema(example = "1")
    Long authorId, 
    @Schema(example = "761")
    Integer pageCount) {
    
}
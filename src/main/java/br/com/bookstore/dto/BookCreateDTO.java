package br.com.bookstore.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCreateDTO(@NotBlank String title, @NotNull BigDecimal price, @NotNull Long authorId, @NotNull int pageCount) {
    
}

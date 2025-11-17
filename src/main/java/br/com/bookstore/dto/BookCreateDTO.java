package br.com.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCreateDTO(@NotBlank String title, @NotNull Long authorId, @NotNull int pageCount) {
    
}

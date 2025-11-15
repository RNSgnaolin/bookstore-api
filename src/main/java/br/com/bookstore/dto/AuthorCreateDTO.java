package br.com.bookstore.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorCreateDTO(@NotBlank String name) {
    
}
package br.com.bookstore.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorCreateDTO(@NotBlank(message = "Nome não pode estar vazio") String name) {
    
}
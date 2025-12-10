package br.com.bookstore.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCreateDTO(
    @NotBlank(message = "Título não pode estar vazio") String title, 
    @NotNull(message = "Preço inválido") BigDecimal price, 
    @NotNull(message = "ID do Autor não pode estar vazio") Long authorId, 
    @NotNull(message = "Número de páginas não pode ser vazio") int pageCount) {
    
}

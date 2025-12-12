package br.com.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthorCreateDTO(
    @Schema(example = "George R. R. Martin")
    @NotBlank(message = "Nome não pode estar vazio") 
    String name) {
    
}
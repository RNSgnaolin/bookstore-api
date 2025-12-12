package br.com.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthorUpdateDTO(
    @Schema(example = "George R. R. Martin")
    String name) {
    
}

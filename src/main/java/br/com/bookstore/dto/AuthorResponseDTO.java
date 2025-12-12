package br.com.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import br.com.bookstore.entity.Author;


public record AuthorResponseDTO(
    @Schema(example = "1")
    Long id, 
    @Schema(example = "George R. R. Martin")
    String name) {
    public AuthorResponseDTO(Author author) {
        this(author.getId(), author.getName());
    }
}

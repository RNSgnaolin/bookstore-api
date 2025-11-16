package br.com.bookstore.dto;

import br.com.bookstore.entity.Author;

public record AuthorResponseDTO(Long id, String name) {
    public AuthorResponseDTO(Author author) {
        this(author.getId(), author.getName());
    }
}

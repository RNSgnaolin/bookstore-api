package br.com.bookstore.dto;

import java.util.Optional;

public record BookUpdateDTO(Optional<String> title, Optional<Long> authorId, Optional<Integer> pageCount) {
    
}
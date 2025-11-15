package br.com.bookstore.dto;

public record BookResponseDTO(Long id, String title, Long stock, AuthorResponseDTO author) {
    
}
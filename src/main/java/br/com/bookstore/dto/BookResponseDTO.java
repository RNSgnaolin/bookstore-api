package br.com.bookstore.dto;

import java.math.BigDecimal;

import br.com.bookstore.entity.Book;

public record BookResponseDTO(Long id, String title, BigDecimal price, int pageCount, int stock, AuthorResponseDTO author) {
    public BookResponseDTO(Book book) {
        this(book.getId(), book.getTitle(), book.getPrice(), book.getPageCount(), book.getStock(), new AuthorResponseDTO(book.getAuthor()));
    }
}
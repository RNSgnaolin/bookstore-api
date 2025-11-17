package br.com.bookstore.dto;

import br.com.bookstore.entity.Book;

public record BookResponseDTO(Long id, String title, int pageCount, int stock, AuthorResponseDTO author) {
    public BookResponseDTO(Book book) {
        this(book.getId(), book.getTitle(), book.getPageCount(), book.getStock(), new AuthorResponseDTO(book.getAuthor()));
    }
}
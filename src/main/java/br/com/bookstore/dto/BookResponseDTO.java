package br.com.bookstore.dto;

import java.math.BigDecimal;

import br.com.bookstore.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;

public record BookResponseDTO(
    @Schema(example = "1")
    Long id, 
    @Schema(example = "A Feast of Crows")
    String title, 
    @Schema(example = "70.51")
    BigDecimal price, 
    @Schema(example = "753")
    int pageCount, 
    @Schema(example = "12")
    int stock,  
    AuthorResponseDTO author) {
    public BookResponseDTO(Book book) {
        this(book.getId(), book.getTitle(), book.getPrice(), book.getPageCount(), book.getStock(), new AuthorResponseDTO(book.getAuthor()));
    }
}
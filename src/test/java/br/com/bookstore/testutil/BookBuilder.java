package br.com.bookstore.testutil;

import java.math.BigDecimal;

import br.com.bookstore.dto.AuthorCreateDTO;
import br.com.bookstore.dto.BookCreateDTO;
import br.com.bookstore.dto.BookUpdateDTO;
import br.com.bookstore.entity.Author;
import br.com.bookstore.entity.Book;

/*
    Essa classe cria entidades Book com valores padrão que podem ser editados.

    A intenção é usar somente para facilitar testes.
*/

public class BookBuilder {
    
    private String title;
    private BigDecimal price;
    private int pageCount;
    private Author author;
    private Long authorId;

    public static BookBuilder aBook() {
        return new BookBuilder().withTitle("Title")
            .withPrice(99)
            .withPageCount(99)
            .withAuthor(new Author(new AuthorCreateDTO("Name")))
            .withAuthorId(1L);
    }
    
    public BookBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
    
    public BookBuilder withPrice(double price) {
        this.price = BigDecimal.valueOf(price);
        return this;
    }

    public BookBuilder withPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public BookBuilder withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public BookBuilder withAuthorId(Long id) {
        this.authorId = id;
        return this;
    }

    public BookBuilder withAuthor(AuthorCreateDTO data) {
        this.author = new Author(data);
        return this;
    }

    public Book build() {
        return new Book(title, author, price, pageCount);
    }

    public BookCreateDTO buildCreateDTO() {
        return new BookCreateDTO(title, price, this.authorId, pageCount);
    }

    public BookUpdateDTO buildUpdateDTO() {
        return new BookUpdateDTO(title, price, this.authorId, Integer.valueOf(pageCount));
    }

}

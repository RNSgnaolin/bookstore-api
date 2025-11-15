package br.com.bookstore.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bookstore.dto.BookCreateDTO;
import br.com.bookstore.entity.Author;
import br.com.bookstore.entity.Book;
import br.com.bookstore.repository.AuthorRepository;
import br.com.bookstore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BookService {
    
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Transactional
    public Book create(BookCreateDTO data) {
        Author author = authorRepository.findById(data.authorId()).orElseThrow(EntityNotFoundException::new);
        return bookRepository.save(new Book(data.title(), author));
    }
}

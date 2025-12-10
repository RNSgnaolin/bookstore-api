package br.com.bookstore.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bookstore.dto.BookCreateDTO;
import br.com.bookstore.dto.BookResponseDTO;
import br.com.bookstore.dto.BookUpdateDTO;
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
        Author author = authorRepository.findById(data.authorId()).orElseThrow(() -> new EntityNotFoundException("ID não encontrado: " + data.authorId()));
        return bookRepository.save(new Book(data.title().trim(), author, data.price(), data.pageCount()));
    }

    public BookResponseDTO findById(Long id) {
        return bookRepository.findById(id).map(BookResponseDTO::new)
            .orElseThrow(() -> new EntityNotFoundException("ID não encontrado: " + id));
    }

    public Page<BookResponseDTO> findByQuery(String searchPattern, Pageable pageable) {
        return bookRepository.findByQuery(searchPattern, pageable).map(BookResponseDTO::new);
    }

    public Page<BookResponseDTO> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(BookResponseDTO::new);
    }

    @Transactional
    public BookResponseDTO update(Long id, BookUpdateDTO data) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID não encontrado: " + id));

        Optional.ofNullable(data.title())
            .filter(d -> !d.isBlank())
            .ifPresent(book::setTitle);

        Optional.ofNullable(data.pageCount())
            .ifPresent(d -> book.setPageCount(d));

        Optional.ofNullable(data.price())
            .ifPresent(book::setPrice);
        
        Optional.ofNullable(data.authorId())
            .ifPresent(value -> 
                book.setAuthor(
                    authorRepository.findById(value)
                    .orElseThrow(() -> new EntityNotFoundException("ID não encontrado: " + value))  
                )
            );

        return new BookResponseDTO(book);
    }

    @Transactional
    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID não encontrado: " + id));
        book.setDeleted(true);
    }


}

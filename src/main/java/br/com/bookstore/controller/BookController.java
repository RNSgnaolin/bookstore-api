package br.com.bookstore.controller;

import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.bookstore.dto.BookCreateDTO;
import br.com.bookstore.dto.BookResponseDTO;
import br.com.bookstore.entity.Book;
import br.com.bookstore.service.AuthorService;
import br.com.bookstore.service.BookService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
    
    public BookController(BookService service) {
        this.service = service;
    }

    private BookService service;

    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> findBooks(@RequestParam(value = "query") Optional<String> searchPattern, Pageable pageable) {

        if (searchPattern.isPresent()) {
            return ResponseEntity.ok(service.findByQuery(searchPattern.get(), pageable)
            .map(BookResponseDTO::new));
        }

        return ResponseEntity.ok(service.findAll(pageable).map(BookResponseDTO::new));

    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookCreateDTO data, UriComponentsBuilder builder) {
        
        Book book = service.create(data);
        var uri = builder.path("/books/{id}").buildAndExpand(book.getId()).toUri();

        return ResponseEntity.created(uri).body(new BookResponseDTO(book));
    }
}

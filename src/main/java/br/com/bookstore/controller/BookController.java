package br.com.bookstore.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.bookstore.dto.BookCreateDTO;
import br.com.bookstore.dto.BookResponseDTO;
import br.com.bookstore.dto.BookUpdateDTO;
import br.com.bookstore.entity.Book;
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
    public ResponseEntity<Page<BookResponseDTO>> findBooks(
        @RequestParam(value = "query", required = false) String searchPattern, 
        @PageableDefault(sort = "title", direction = Sort.Direction.ASC)
        Pageable pageable) {

        if (searchPattern != null) 
            return ResponseEntity.ok(service.findByQuery(searchPattern, pageable));

        return ResponseEntity.ok(service.findAll(pageable));

    }

    @GetMapping("/list")
    public ResponseEntity<List<BookResponseDTO>> findBookList(@RequestParam(value = "query", required = false) String searchPattern) {
        
        if (searchPattern != null) {
            return ResponseEntity.ok(service.findByQuery(searchPattern, PageRequest.of(0, 30)).toList());
        }

        return ResponseEntity.ok(service.findAll(PageRequest.of(0, 30)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> findBook(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookCreateDTO data, UriComponentsBuilder builder) {
        
        Book book = service.create(data);
        var uri = builder.path("/books/{id}").buildAndExpand(book.getId()).toUri();

        return ResponseEntity.created(uri).body(new BookResponseDTO(book));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookUpdateDTO data) {
        return ResponseEntity.ok(service.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookResponseDTO> deleteBook(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

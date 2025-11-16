package br.com.bookstore.controller;

import java.util.Optional;

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

import br.com.bookstore.dto.AuthorCreateDTO;
import br.com.bookstore.dto.AuthorResponseDTO;
import br.com.bookstore.entity.Author;
import br.com.bookstore.service.AuthorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    
    public AuthorController(AuthorService service) {
        this.service = service;
    }

    private AuthorService service;

    @GetMapping
    public ResponseEntity<Page<AuthorResponseDTO>> findAuthors(Pageable pageable, @RequestParam(value = "query") Optional<String> searchPattern) {

        if (searchPattern.isPresent()) {
            return ResponseEntity.ok(service.findByQuery(searchPattern.get(), pageable));
        }

        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody @Valid AuthorCreateDTO data, UriComponentsBuilder builder) {

        Author author = service.create(data);
        var uri = builder.path("/authors/{id}").buildAndExpand(author.getId()).toUri();

        return ResponseEntity.created(uri).body(new AuthorResponseDTO(author));
    }
}

package br.com.bookstore.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bookstore.dto.AuthorResponseDTO;
import br.com.bookstore.service.AuthorService;

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
            return ResponseEntity.ok(service.findByQuery(searchPattern.get(), pageable)
            .map(AuthorResponseDTO::new));
        }

        return ResponseEntity.ok(service.findAll(pageable).map(AuthorResponseDTO::new));
    }
}

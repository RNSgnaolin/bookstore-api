package br.com.bookstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bookstore.dto.AuthorCreateDTO;
import br.com.bookstore.entity.Author;
import br.com.bookstore.repository.AuthorRepository;

@Service
public class AuthorService {
    
    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    private AuthorRepository repository;

    @Transactional
    public Author create(AuthorCreateDTO data) {
        Author author = new Author(data);
        return repository.save(author);
    }

    public Page<Author> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Author> findByQuery(String searchPattern, Pageable pageable) {
        return repository.findByQuery(searchPattern, pageable);
    }
}

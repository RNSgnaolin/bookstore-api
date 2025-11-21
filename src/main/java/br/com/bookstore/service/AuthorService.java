package br.com.bookstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bookstore.dto.AuthorCreateDTO;
import br.com.bookstore.dto.AuthorResponseDTO;
import br.com.bookstore.dto.AuthorUpdateDTO;
import br.com.bookstore.entity.Author;
import br.com.bookstore.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;

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

    public AuthorResponseDTO findById(Long id) {
        return repository.findById(id).map(AuthorResponseDTO::new)
            .orElseThrow(EntityNotFoundException::new);
    }

    public Page<AuthorResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(AuthorResponseDTO::new);
    }

    public Page<AuthorResponseDTO> findByQuery(String searchPattern, Pageable pageable) {
        return repository.findByQuery(searchPattern, pageable).map(AuthorResponseDTO::new);
    }

    @Transactional
    public AuthorResponseDTO update(Long id, AuthorUpdateDTO data) {
        Author author = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        data.name().ifPresent(author::setName);

        return new AuthorResponseDTO(author);
    }
}

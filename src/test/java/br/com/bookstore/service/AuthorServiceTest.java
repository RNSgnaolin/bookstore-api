package br.com.bookstore.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.bookstore.repository.AuthorRepository;

public class AuthorServiceTest {
    
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    // Implementation TBD
}

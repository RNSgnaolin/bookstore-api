package br.com.bookstore.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.bookstore.repository.AuthorRepository;
import br.com.bookstore.repository.BookRepository;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;
    
}

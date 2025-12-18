package br.com.bookstore.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.bookstore.dto.AuthorCreateDTO;
import br.com.bookstore.dto.BookCreateDTO;
import br.com.bookstore.dto.BookUpdateDTO;
import br.com.bookstore.entity.Author;
import br.com.bookstore.entity.Book;
import br.com.bookstore.exceptions.domain.AuthorNotFoundException;
import br.com.bookstore.exceptions.domain.BookNotFoundException;
import br.com.bookstore.repository.AuthorRepository;
import br.com.bookstore.repository.BookRepository;
import br.com.bookstore.testutil.BookBuilder;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;
    
    @Test
    void updateBook_whenPatchContainsValidValues_updatesBook() {

        Book actual = BookBuilder.aBook().build();

        Book expected = BookBuilder.aBook()
            .withTitle("New Title")
            .withPrice(32)
            .withPageCount(12).build();

        BookUpdateDTO update = BookBuilder.aBook()
                                .withTitle("New Title")
                                .withPrice(32)
                                .withAuthorId(null)
                                .withPageCount(12)
                                .buildUpdateDTO();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(actual));

        bookService.update(1L, update);

        Assertions.assertAll("updated book",
            () -> Assertions.assertEquals(expected.getTitle(), actual.getTitle()),
            () -> Assertions.assertEquals(expected.getPrice(), actual.getPrice()),
            () -> Assertions.assertEquals(expected.getPageCount(), actual.getPageCount()),
            () -> Assertions.assertEquals(expected.getStock(), actual.getStock())
        );
    }

    @Test
    void updateBook_whenPatchContainsEmptyAndNull_keepOriginalValues() {

        Book actual = BookBuilder.aBook().build();
        Book expected = BookBuilder.aBook().build();

        BookUpdateDTO update = new BookUpdateDTO(" ", null, null, null);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(actual));

        bookService.update(1L, update);

        Assertions.assertAll("updated book",
            () -> Assertions.assertEquals(expected.getTitle(), actual.getTitle()),
            () -> Assertions.assertEquals(expected.getPrice(), actual.getPrice()),
            () -> Assertions.assertEquals(expected.getPageCount(), actual.getPageCount()),
            () -> Assertions.assertEquals(expected.getStock(), actual.getStock())
        );
    }

    @Test
    void updateBook_whenAuthorDoesNotExist_throwsAuthorNotFoundException() {

        Book actual = BookBuilder.aBook().build();

        BookUpdateDTO update = BookBuilder.aBook()
                                .withTitle("New Title")
                                .withPrice(null)
                                .withPageCount(32)
                                .buildUpdateDTO();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(actual));
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(AuthorNotFoundException.class, () -> bookService.update(1L, update));
    }

    @Test
    void updateBook_whenBookDoesNotExist_throwsBookNotFoundException() {

        BookUpdateDTO update = BookBuilder.aBook().buildUpdateDTO();

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.update(1L, update));
    }

    @Test
    void createBook_whenBookTitleHasEmptySpaces_trimSpacesFromTitle() {

        BookCreateDTO data = BookBuilder.aBook().withTitle("  Title    ").buildCreateDTO();
        Author author = new Author(new AuthorCreateDTO("Name"));

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.create(data);

        Assertions.assertEquals("Title", result.getTitle());
        verify(bookRepository).save(any(Book.class));
    }
}

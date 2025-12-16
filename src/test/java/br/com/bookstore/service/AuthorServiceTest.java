package br.com.bookstore.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.bookstore.dto.AuthorCreateDTO;
import br.com.bookstore.dto.AuthorUpdateDTO;
import br.com.bookstore.entity.Author;
import br.com.bookstore.exceptions.domain.AuthorNotFoundException;
import br.com.bookstore.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void updateAuthor_whenPatchContainsValidValues_updateAuthor() {

        Author actual = new Author(new AuthorCreateDTO("Name"));
        Author expected = new Author(new AuthorCreateDTO("New Name"));

        AuthorUpdateDTO update = new AuthorUpdateDTO("New Name");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(actual));

        authorService.update(1L, update);

        Assertions.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void updateAuthor_whenPatchContainsEmptyAndNull_keepOriginalValues() {

        Author actual = new Author(new AuthorCreateDTO("Name"));
        Author expected = new Author(new AuthorCreateDTO("Name"));
        
        AuthorUpdateDTO updateEmpty = new AuthorUpdateDTO(" ");
        AuthorUpdateDTO updateNull = new AuthorUpdateDTO(null);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(actual));

        authorService.update(1L, updateEmpty);

        Assertions.assertEquals(expected.getName(), actual.getName());

        authorService.update(1L, updateNull);

        Assertions.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void updateAuthor_whenAuthorDoesNotExist_throwsAuthorNotFoundException() {

        AuthorUpdateDTO update = new AuthorUpdateDTO("New Author");

        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(AuthorNotFoundException.class, () -> authorService.update(1L, update));
    }
}

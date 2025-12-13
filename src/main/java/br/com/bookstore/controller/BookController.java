package br.com.bookstore.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
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
import br.com.bookstore.exceptions.ErrorResponse;
import br.com.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
    
    public BookController(BookService service) {
        this.service = service;
    }

    private BookService service;

    @GetMapping
    @ApiResponse(responseCode = "200")
    public ResponseEntity<Page<BookResponseDTO>> findBooks(
        @Parameter(description = "Filtro com base no título do livro ou nome do autor", example = "Game of")
        @RequestParam(value = "query", required = false) String searchPattern, 
        @PageableDefault(sort = "title", direction = Sort.Direction.ASC)
        @ParameterObject Pageable pageable) {

        if (searchPattern != null) 
            return ResponseEntity.ok(service.findByQuery(searchPattern, pageable));

        return ResponseEntity.ok(service.findAll(pageable));

    }

    @GetMapping("/list")
    @ApiResponse(responseCode = "200")
    @Operation(description = "Lista de livros sem paginação, útil para popular UI")
    public ResponseEntity<List<BookResponseDTO>> findBookList(
        @Parameter(description = "Filtro com base no título do livro ou nome do autor", example = "Game of")
        @RequestParam(value = "query", required = false) String searchPattern
    ) {
        
        if (searchPattern != null) {
            return ResponseEntity.ok(service.findByQuery(searchPattern, PageRequest.of(0, 30)).toList());
        }

        return ResponseEntity.ok(service.findAll(PageRequest.of(0, 30)).toList());
    }

    @GetMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<BookResponseDTO> findBook(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/author/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Page<BookResponseDTO>> findBookByAuthor(@PathVariable Long id, 
        @PageableDefault(sort = "title", direction = Sort.Direction.ASC) 
        @ParameterObject Pageable pageable) {
            
        return ResponseEntity.ok(service.findByAuthor(id, pageable));
    }

    @PostMapping
    @ApiResponses({
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookCreateDTO data, UriComponentsBuilder builder) {
        
        Book book = service.create(data);
        var uri = builder.path("/books/{id}").buildAndExpand(book.getId()).toUri();

        return ResponseEntity.created(uri).body(new BookResponseDTO(book));
    }

    @PatchMapping("/{id}")
    @Operation(description = "Atualiza um livro com o ID providenciado no endereço. Os campos no corpo do JSON não são mandatórios, pode-se enviar apenas o que quer ser atualizado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookUpdateDTO data) {
        return ResponseEntity.ok(service.update(id, data));
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = @Content),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<BookResponseDTO> deleteBook(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

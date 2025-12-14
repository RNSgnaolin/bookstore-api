package br.com.bookstore.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.bookstore.dto.AuthorCreateDTO;
import br.com.bookstore.dto.AuthorResponseDTO;
import br.com.bookstore.dto.AuthorUpdateDTO;
import br.com.bookstore.entity.Author;
import br.com.bookstore.exceptions.ErrorResponse;
import br.com.bookstore.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    
    public AuthorController(AuthorService service) {
        this.service = service;
    }

    private AuthorService service;

    @GetMapping
    @ApiResponse(responseCode = "200")
    @Operation(summary = "Retorna uma lista paginada de autores", description = """
            Paginação:

            - page: índice da página (baseado em zero; padrão: 0)
            - size: quantidade de registros por página (padrão: 30)
            - sort: critério de ordenação no formato campo,asc|desc (padrão: name,asc)

            Exemplo: 

            /authors?query=George+R&page=2&size=10
            """)
    public ResponseEntity<Page<AuthorResponseDTO>> findAuthors(
        @Parameter(description = "Filtro opcional com base no nome do autor", example = "Martin")
        @RequestParam(value = "query", required = false) String searchPattern,
        @PageableDefault(sort = "name", direction = Sort.Direction.ASC)
        @Parameter(hidden = true) Pageable pageable) {

        if (searchPattern != null) {
            return ResponseEntity.ok(service.findByQuery(searchPattern, pageable));
        }

        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/list")
    @ApiResponse(responseCode = "200")
    @Operation(summary = "Retorna uma lista de autores sem paginação", description = "Útil para popular menus de seleção. Retorna um máximo de 30 elementos")
    public ResponseEntity<List<AuthorResponseDTO>> findAuthorsList(
        @Parameter(description = "Filtro com base no nome do autor", example = "Martin")
        @RequestParam(value = "query", required = false) String searchPattern) {
        
        if (searchPattern != null) {
            return ResponseEntity.ok(
                service.findByQuery(searchPattern, PageRequest.of(0, 30))
                .toList());
        }

        return ResponseEntity.ok(service.findAll(PageRequest.of(0, 30)).toList());
    }

    @GetMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<AuthorResponseDTO> findAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @ApiResponses({
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody @Valid AuthorCreateDTO data, UriComponentsBuilder builder) {

        Author author = service.create(data);
        var uri = builder.path("/authors/{id}").buildAndExpand(author.getId()).toUri();

        return ResponseEntity.created(uri).body(new AuthorResponseDTO(author));
    }

    @PatchMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<AuthorResponseDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorUpdateDTO data) {
        return ResponseEntity.ok(service.update(id, data));
    }
}

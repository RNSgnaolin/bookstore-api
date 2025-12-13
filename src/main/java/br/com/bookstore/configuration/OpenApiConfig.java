package br.com.bookstore.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "Bookstore API",
        description = "API em Spring Boot para operações CRUD nas entidades Livro (Book) e Autor (Author). Demonstra design REST, relação ManyToOne, e tratamento de erros com respostas informativas ao cliente que consome a API.",
        license = @License(name = "MIT License")
    ),
    servers = {
        @Server(url = "http://localhost:8080")
    }
)
public class OpenApiConfig { }

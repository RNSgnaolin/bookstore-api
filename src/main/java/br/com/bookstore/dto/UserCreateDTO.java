package br.com.bookstore.dto;

import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
    @NotBlank(message = "Nome de usuário não pode ser vazio") String name, 
    @NotBlank(message = "Login não pode ser vazio") String login, 
    @NotBlank(message = "Senha não pode ser vazia") String password) {
    
}

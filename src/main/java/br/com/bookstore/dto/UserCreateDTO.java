package br.com.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
    @NotBlank(message = "Nome de usuário não pode ser vazio") 
    @Schema(example = "RNSgnaolin")
    String name, 
    @NotBlank(message = "Login não pode ser vazio") 
    @Schema(example = "rns")
    String login, 
    @NotBlank(message = "Senha não pode ser vazia")
    @Schema(example = "123change")
    String password) {
    
}

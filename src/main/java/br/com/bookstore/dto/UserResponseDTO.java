package br.com.bookstore.dto;

import br.com.bookstore.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponseDTO(
    @Schema(example = "1")
    Long id, 
    @Schema(example = "RNSgnaolin")
    String name) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getName());
    }
}
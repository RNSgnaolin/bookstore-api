package br.com.bookstore.dto;

import br.com.bookstore.entity.User;

public record UserResponseDTO(Long id, String name) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getName());
    }
}
package br.com.bookstore.dto;

import java.util.Optional;

public record UserUpdateDTO(Optional<String> name, Optional<String> login, Optional<String> password) {
    
}

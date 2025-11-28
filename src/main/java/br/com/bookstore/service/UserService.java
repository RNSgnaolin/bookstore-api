package br.com.bookstore.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bookstore.dto.UserResponseDTO;
import br.com.bookstore.dto.UserUpdateDTO;
import br.com.bookstore.entity.User;
import br.com.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    private UserRepository repository;

    @Transactional
    public UserResponseDTO update (Long id, UserUpdateDTO data) {
        
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        Optional.ofNullable(data.name())
            .filter(d -> !d.isBlank())
            .ifPresent(user::setName);

        Optional.ofNullable(data.login())
            .filter(d -> !d.isBlank())
            .ifPresent(user::setLogin);

        // Ensure to encrypt password when Spring Security is set up
        Optional.ofNullable(data.password())
            .filter(d -> !d.isBlank())
            .ifPresent(user::setPassword);
        
        return new UserResponseDTO(user);
    }

    public UserResponseDTO findById (Long id) {
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new UserResponseDTO(user);
    }

    
}

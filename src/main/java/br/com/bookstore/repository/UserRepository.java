package br.com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bookstore.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}

package br.com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bookstore.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
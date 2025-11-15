package br.com.bookstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Entity(name = "Book")
@Table(name = "books")
@EqualsAndHashCode(of = "id")
public class Book {

    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
        this.stock = 0l;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Long stock;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}

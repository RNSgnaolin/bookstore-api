package br.com.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Book")
@Table(name = "books")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Book {

    public Book(String title, Author author, int pageCount) {
        this.title = title;
        this.author = author;
        this.stock = 0;
        this.pageCount = pageCount;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "page_count")
    private int pageCount;

    private int stock;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}

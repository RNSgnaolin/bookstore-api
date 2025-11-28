package br.com.bookstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.bookstore.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
            SELECT b FROM Book b JOIN b.author a
            WHERE (
            LOWER(b.title) LIKE LOWER(CONCAT('%', :pattern, '%'))
            OR LOWER(a.name) LIKE LOWER(CONCAT('%', :pattern, '%'))
            )
            AND b.deleted = false
            """)
    public Page<Book> findByQuery(@Param("pattern") String searchPattern, Pageable pageable);

    @Query("""
        SELECT b FROM Book b JOIN b.author a
        WHERE (
        LOWER(b.title) LIKE LOWER(CONCAT('%', :pattern, '%'))
        OR LOWER(a.name) LIKE LOWER(CONCAT('%', :pattern, '%'))
        )
        AND b.deleted = true
        """)
    public Page<Book> findDeletedByQuery(@Param("pattern") String searchPattern, Pageable pageable);
}
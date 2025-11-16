package br.com.bookstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.bookstore.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    @Query("""
            SELECT a from Author a 
            WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :pattern, '%'))
            """)
    public Page<Author> findByQuery(@Param("pattern") String searchPattern, Pageable pageable);
}

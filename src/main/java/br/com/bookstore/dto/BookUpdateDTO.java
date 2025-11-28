package br.com.bookstore.dto;

import java.math.BigDecimal;

public record BookUpdateDTO(String title, BigDecimal price, Long authorId, Integer pageCount) {
    
}
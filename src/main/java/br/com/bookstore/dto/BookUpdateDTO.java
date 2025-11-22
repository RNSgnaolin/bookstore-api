package br.com.bookstore.dto;

import java.math.BigDecimal;
import java.util.Optional;

public record BookUpdateDTO(Optional<String> title, Optional<BigDecimal> price, Optional<Long> authorId, Optional<Integer> pageCount) {
    
}
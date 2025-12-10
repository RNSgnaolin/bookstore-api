package br.com.bookstore.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import jakarta.persistence.EntityNotFoundException;

@Component
public class ExceptionMapper {

    public Map<String, String> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(field -> errors.put(field.getField(), field.getDefaultMessage()));
        return errors;
    }

    public Map<String, String> handleException(EntityNotFoundException ex) {
        return Map.of("id", ex.getMessage());
    }

    public Map<String, String> handleException(DataIntegrityViolationException ex) {

        String msg = ex.getMostSpecificCause() != null ? 
        ex.getMostSpecificCause().getMessage().toLowerCase() : ex.getMessage().toLowerCase();

        Map<String, String> errors = new HashMap<>();

        if (msg.contains("uq_book_title_author")) {
            errors.put("title", "Título já existente para o autor indicado");
        } 
        else if (msg.contains("uidx_authors_name")) {
            errors.put("name", "Um autor com este nome já existe");
        }
        else if (msg.contains("uidx_users_login")) {
            errors.put("login", "Login já sendo utilizado");
        }

        else if (msg.contains("chk_books_stock_positive")) {
            errors.put("stock", "O estoque deve ser zero ou positivo");
        }
        else if (msg.contains("chk_books_pages_positive")) {
            errors.put("pageCount", "O número de páginas deve ser maior que zero");
        }

        else {
            errors.put("database", "Uma regra do banco de dados foi violada");
        }

        return errors;
    }

    public Map<String, String> handleException(BindException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(field -> {
            errors.put(field.getField(), field.getDefaultMessage());
        });

        return errors;
    }

    public Map<String, String> handleException(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();

        Throwable cause = ex.getCause();

        while (cause != null && !(cause instanceof MismatchedInputException) 
            && !(cause instanceof InvalidFormatException)) {
            
            cause = cause.getCause();

        }

        if (cause instanceof MismatchedInputException mie) {
            
            if (mie.getPath().isEmpty()) errors.put("desconhecido", "Valor inválido para o campo");

            else {
                mie.getPath().forEach(field -> 
                    errors.put(field.getFieldName(), "Tipo inválido para o campo")
                );
            }
        }

        else if (cause instanceof InvalidFormatException ife) {

            if (ife.getPath().isEmpty()) errors.put("desconhecido", "Valor inválido para o campo");

            else {

                ife.getPath().forEach(field -> {
                    Object wrong = ife.getValue();
                    String expected = ife.getTargetType().getSimpleName();
                    String msg = "Valor " + wrong + " não é válido para o campo, esperado: " + expected;

                    errors.put(field.getFieldName(), msg); 
                });
            }
        }

        else {
            errors.put("body", "O JSON enviado é ilegível");
        }

        return errors;
    }

}

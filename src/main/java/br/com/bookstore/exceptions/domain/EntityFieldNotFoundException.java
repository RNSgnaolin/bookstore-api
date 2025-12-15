package br.com.bookstore.exceptions.domain;

import jakarta.persistence.EntityNotFoundException;

/*
    Subclasses devem providenciar o campo, valor e entidade para que o ExceptionMapper
    possa modelar o ErrorResponse.

    Esse template serve para garantir mensagens de erro mais precisos
    para quem consome a API.
*/
public abstract class EntityFieldNotFoundException extends EntityNotFoundException {
    protected EntityFieldNotFoundException() {
        super();
    }

    public abstract String getFieldName();
    public abstract String getFieldValue();
    public abstract String getEntity();

    @Override
    public String getMessage() {
        return "Valor " + getFieldValue() + " não encontrado para entidade " + getEntity();
    }
}
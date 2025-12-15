package br.com.bookstore.exceptions.domain;

public class BookNotFoundException extends EntityFieldNotFoundException {
    public BookNotFoundException(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    private String fieldName;
    private String fieldValue;

    public String getFieldName() {
        return this.fieldName;
    }

    public String getFieldValue() {
        return this.fieldValue;
    }

    public String getEntity() {
        return "Book";
    }

}

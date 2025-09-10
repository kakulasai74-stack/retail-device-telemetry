package com.emf.ingestion.error;

public class SchemaValidationException extends RuntimeException {
    public SchemaValidationException(String message) { super(message); }
}

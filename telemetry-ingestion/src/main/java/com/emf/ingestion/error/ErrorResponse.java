package com.emf.ingestion.error;

import java.time.Instant;
import java.util.Map;

public class ErrorResponse {
    private String error;                 // e.g. "Bad Request"
    private String message;               // human-friendly explanation
    private String path;                  // request path, e.g. /api/v1/telemetry
    private Instant timestamp;            // when the error happened
    private Map<String, String> fieldErrors; // optional: field -> message

    public ErrorResponse() {}

    public ErrorResponse(String error, String message, String path, Instant timestamp, Map<String, String> fieldErrors) {
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
        this.fieldErrors = fieldErrors;
    }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public Map<String, String> getFieldErrors() { return fieldErrors; }
    public void setFieldErrors(Map<String, String> fieldErrors) { this.fieldErrors = fieldErrors; }
}

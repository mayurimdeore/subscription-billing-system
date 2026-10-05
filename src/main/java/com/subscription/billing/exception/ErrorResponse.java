package com.subscription.billing.exception;

import java.time.LocalDateTime;

import java.util.List;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
    private List<String> errors;
    
    

    public ErrorResponse() {
    }

    public ErrorResponse(LocalDateTime timestamp, int status,
            String message, String path,
            List<String> errors) {
            this.timestamp = timestamp;
            this.status = status;
            this.message = message;
            this.path = path;
            this.errors = errors;
    }
    
    
    public List<String> getErrors() {
        return errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
package org.example;

import java.util.HashMap;
import java.util.Map;

public class ValidationErrorResponse extends RuntimeException {
    private String errorMessage = "Validation error";
    private Map<String, String> details = new HashMap<>();
    private int errorCode = 400;

    public ValidationErrorResponse(Map<String, String> details) {
        this.details = details;
    }
}

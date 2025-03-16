package org.example.exception;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {
    private Map<String, String> errorResponse = new LinkedHashMap<>();

    public ValidationException(Map<String, String> details) {
        errorResponse.put("errorMessage", "Validation error");
        errorResponse.putAll(details);
        errorResponse.put("errorCode", "400");
    }
}

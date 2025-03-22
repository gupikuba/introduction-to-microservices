package org.example.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ValidationErrorResponse {
    private final String errorMessage = "Validation error";
    private final int errorCode = 400;
    private Map<String, String> details = new HashMap<>();

    public void addDetail(String field, String message) {
        details.put(field, message);
    }

}

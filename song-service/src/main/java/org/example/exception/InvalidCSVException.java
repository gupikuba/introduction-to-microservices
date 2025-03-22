package org.example.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties({"cause", "stackTrace", "suppressed", "message", "localizedMessage"})
@Getter
public class InvalidCSVException extends RuntimeException {
    private final String errorMessage;
    private final int errorCode = 400;
    public InvalidCSVException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

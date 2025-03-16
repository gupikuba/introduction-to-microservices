package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties({"cause", "stackTrace", "suppressed", "message", "localizedMessage"})
@Getter
public class InvalidCSVException extends RuntimeException {
    private final String errorMessage;
    private final int errorCode;
    public InvalidCSVException(String idsString, int errorCode) {
        this.errorMessage =
                String.format("%s - CSV string format is invalid or length=%d exceeds 200 chars", idsString, idsString.length());
        this.errorCode = errorCode;
    }
}

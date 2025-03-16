package org.example.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Mp3NotFoundException extends RuntimeException{
    private final Integer id;

    public String getMessage() {
        return String.format("Resource with Id=%s not found", id);
    }
}

package org.example.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MetadataAlreadyExistsException extends RuntimeException {
    private final int id;

    public String getMessage() {
        return String.format("Metadat with id=%d already exists", id);
    }
}

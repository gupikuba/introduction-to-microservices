package org.example.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MetadataAlreadyExistsException extends RuntimeException {
    int id;

    public String getMessage() {
        return String.format("Metadat with id=%d already exists", id);
    }
}

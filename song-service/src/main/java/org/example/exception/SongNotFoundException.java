package org.example.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SongNotFoundException extends RuntimeException {
    int id;

    public String getMessage() {
        return String.format("Song metadata with id=%d doesn't exist", id);
    }
}

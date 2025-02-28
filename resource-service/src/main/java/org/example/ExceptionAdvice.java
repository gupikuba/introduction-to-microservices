package org.example;

import jakarta.xml.bind.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(InvalidMp3Exception.class)
    public ResponseEntity<String> invalidMp3(InvalidMp3Exception exception) {
        return ResponseEntity.badRequest().body("Bad request");
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> serverException() {
        return ResponseEntity.internalServerError().body("An error occurred on the server.");
    }

    @ExceptionHandler(value = {Mp3NotFoundException.class})
    public ResponseEntity<String> notFound() {
        return ResponseEntity.notFound().build();
    }
}

package org.example.exception.handler;

import org.example.dto.ErrorMessageDTO;
import org.example.exception.InvalidCSVException;
import org.example.exception.Mp3NotFoundException;
import org.example.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
public class ResourceServiceExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> invalidMp3(ValidationException exception) {
        return ResponseEntity.badRequest().body(exception.getErrorResponse());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> serverException(Exception e) {
        return ResponseEntity.internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body("An error occurred on the server.");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorMessageDTO> notSupported(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageDTO(
                        String.format("Invalid file format %s. Only MP3 files are allowed", e.getContentType()),
                        HttpStatus.BAD_REQUEST.value()));
    }


    @ExceptionHandler(value = {HandlerMethodValidationException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorMessageDTO> badRequest(Exception exception) {
        String message = String.format("Provided Id=%s is invalid. Must be a positive integer.",
                exception instanceof HandlerMethodValidationException ?
                        ((HandlerMethodValidationException) exception).getParameterValidationResults()
                                .get(0).getArgument()
                        : ((MethodArgumentTypeMismatchException) exception).getValue());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageDTO(message, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(InvalidCSVException.class)
    public ResponseEntity<InvalidCSVException> badRequestInvalidCSV(InvalidCSVException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception);
    }


    @ExceptionHandler(value = {Mp3NotFoundException.class})
    public ResponseEntity<ErrorMessageDTO> notFound(Mp3NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
    }
}

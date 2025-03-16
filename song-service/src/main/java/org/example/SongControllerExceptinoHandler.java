package org.example;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class SongControllerExceptinoHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handle(MethodArgumentNotValidException exception) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        for (FieldError error : exception.getFieldErrors()) {
            response.addDetail(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MetadataAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDTO> alreadyExists(MetadataAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageDTO(exception.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> serverError(RuntimeException exception) {
        return ResponseEntity.internalServerError().body("An error occurred on the server");
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> notFound(SongNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(value = {HandlerMethodValidationException.class, MethodArgumentTypeMismatchException.class,
    ConstraintViolationException.class})
    public ResponseEntity<String> badRequest(Exception exception) {
        return ResponseEntity.badRequest().body(String.format("Provided Id=%s is invalid",
                exception instanceof HandlerMethodValidationException ?
                        ((HandlerMethodValidationException) exception).getParameterValidationResults().get(0).getArgument()
                        : exception instanceof MethodArgumentTypeMismatchException ? ((MethodArgumentTypeMismatchException) exception).getValue()
                        : getValueFromConstraintViolationException((ConstraintViolationException)exception)));
    }

    private Object getValueFromConstraintViolationException(ConstraintViolationException e) {
        return e.getConstraintViolations().stream().findFirst().get().getInvalidValue();
    }

    @ExceptionHandler(InvalidCSVException.class)
    public ResponseEntity<InvalidCSVException> badRequestInvalidCSV(InvalidCSVException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception);
    }
}

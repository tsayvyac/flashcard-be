package com.tsayvyac.flashcard.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "EXCEPTION_HANDLER")
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleCardSetNotFound(NotFoundException ex) {
        log.error("Not found exception", ex);
        return new ResponseEntity<>(getBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnsupportedScoreException.class)
    public ResponseEntity<Object> handleUnsupportedScore(UnsupportedScoreException ex) {
        log.error("Unsupported score exception", ex);
        return new ResponseEntity<>(getBody(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Object> handleValidation(ValidationException ex) {
        log.error("Validation exception", ex);
        return new ResponseEntity<>(getBody(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleArgumentValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    private Map<String, Object> getBody(String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);

        return body;
    }

}

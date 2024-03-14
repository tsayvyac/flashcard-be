package com.tsayvyac.flashcard.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CardSetNotFound.class)
    public ResponseEntity<Object> handleCardSetNotFound(CardSetNotFound ex) {
        log.error("Card set not found", ex);
        return new ResponseEntity<>(getStdBody(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FlashcardNotFound.class)
    public ResponseEntity<Object> handleFlashcardNotFound(FlashcardNotFound ex) {
        log.error("Flashcard not found", ex);
        return new ResponseEntity<>(getStdBody(ex), HttpStatus.NOT_FOUND);
    }

    private Map<String, Object> getStdBody(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());

        return body;
    }
}

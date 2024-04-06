package com.tsayvyac.flashcard.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    private Map<String, Object> getBody(String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);

        return body;
    }

}

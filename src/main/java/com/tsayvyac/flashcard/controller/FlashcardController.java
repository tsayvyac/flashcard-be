package com.tsayvyac.flashcard.controller;

import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcards")
@RequiredArgsConstructor
public class FlashcardController {
    private final FlashcardService flashcardService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FlashcardDto createFlashcard(@RequestBody FlashcardDto dto) {
        return flashcardService.createFlashcard(dto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public FlashcardDto getFlashcardById(@PathVariable Long id) {
        return flashcardService.getFlashcardById(id);
    }

//    @PatchMapping

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlashcard(@PathVariable Long id) {
        flashcardService.deleteFlashcard(id);
    }
}

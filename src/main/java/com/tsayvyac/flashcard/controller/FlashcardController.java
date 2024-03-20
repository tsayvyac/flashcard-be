package com.tsayvyac.flashcard.controller;

import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.dto.ProgressDto;
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

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public FlashcardDto updateFlashcard(@PathVariable Long id, @RequestBody FlashcardDto dto) {
        return flashcardService.updateFlashcard(id, dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlashcard(@PathVariable Long id) {
        flashcardService.deleteFlashcard(id);
    }

    @GetMapping(value = "/{id}/progress", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProgressDto getFlashcardProgress(@PathVariable Long id) {
        return flashcardService.getProgress(id);
    }

    @PatchMapping(value = "/{id}/progress", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProgressDto updateFlashcardProgress(@PathVariable Long id, @RequestBody ProgressDto dto) {
        return flashcardService.updateProgress(id, dto);
    }
}

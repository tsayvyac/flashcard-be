package com.tsayvyac.flashcard.controller;

import com.tsayvyac.flashcard.controller.request.FlashcardRequest;
import com.tsayvyac.flashcard.dto.FlashcardModelDto;
import com.tsayvyac.flashcard.dto.FlashcardRequestDto;
import com.tsayvyac.flashcard.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcards")
@RequiredArgsConstructor
public class FlashcardController {
    private final FlashcardService flashcardService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlashcardModelDto> createFlashcard(@RequestBody FlashcardRequest request) {
        FlashcardRequestDto dto = new FlashcardRequestDto(
                request.front(),
                request.back(),
                request.cardSetId()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(flashcardService.createFlashcard(dto));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public FlashcardModelDto getFlashcard(@PathVariable Long id) {
        return flashcardService.getFlashcard(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlashcard(@PathVariable Long id) {
        flashcardService.deleteFlashcard(id);
    }

}

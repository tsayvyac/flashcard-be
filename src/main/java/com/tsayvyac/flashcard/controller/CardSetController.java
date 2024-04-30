package com.tsayvyac.flashcard.controller;

import com.tsayvyac.flashcard.dto.CardSetDto;
import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.dto.PageDto;
import com.tsayvyac.flashcard.dto.SetsInfoDto;
import com.tsayvyac.flashcard.service.CardSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/sets")
@RequiredArgsConstructor
public class CardSetController {
    private final CardSetService cardSetService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CardSetDto createCardSet(@RequestBody CardSetDto dto) {
        return cardSetService.createCardSet(dto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PageDto<CardSetDto> getCardSets(
            @RequestParam(value = "page", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize
    ) {
        return cardSetService.getCardSets(pageNo, pageSize, false);
    }

    @GetMapping(value = "/rep", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PageDto<CardSetDto> getRepCardSets(
            @RequestParam(value = "page", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize
    ) {
        return cardSetService.getCardSets(pageNo, pageSize, true);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SetsInfoDto> getSetsInfo() {
        return cardSetService.getSetsInfo();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CardSetDto getCardSetById(@PathVariable Long id) {
        return cardSetService.getCardSetById(id);
    }

    @GetMapping(value = "/{id}/flashcards", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PageDto<FlashcardDto> getFlashcardsInSet(
            @PathVariable Long id,
            @RequestParam(value = "page", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize
    ) {
        return cardSetService.getFlashcardsInSet(id, pageNo, pageSize);
    }

    @GetMapping(value = "/{id}/repetition")
    @ResponseStatus(HttpStatus.OK)
    public List<FlashcardDto> getRepetitionFlashcards(@PathVariable Long id) {
        return cardSetService.getFlashcardsFromSet(id, false);
    }

    @GetMapping(value = "/{id}/cram")
    @ResponseStatus(HttpStatus.OK)
    public List<FlashcardDto> getCramFlashcards(@PathVariable Long id) {
        return cardSetService.getFlashcardsFromSet(id, true);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CardSetDto updateCardSet(@PathVariable Long id, @RequestBody CardSetDto dto) {
        return cardSetService.updateCardSet(id, dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCardSet(@PathVariable Long id) {
        cardSetService.deleteCardSet(id);
    }

}

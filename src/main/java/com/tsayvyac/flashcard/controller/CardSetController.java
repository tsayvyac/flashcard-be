package com.tsayvyac.flashcard.controller;

import com.tsayvyac.flashcard.controller.request.CardSetRequest;
import com.tsayvyac.flashcard.dto.CardSetModelDto;
import com.tsayvyac.flashcard.dto.CardSetRequestDto;
import com.tsayvyac.flashcard.service.CardSetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sets")
@RequiredArgsConstructor
@Slf4j
public class CardSetController {
    private final CardSetService cardSetService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardSetModelDto> createCardSet(@RequestBody CardSetRequest request) {
        CardSetModelDto dto = new CardSetModelDto(
                null,
                request.name(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cardSetService.createCardSet(dto));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CardSetModelDto getCardSet(@PathVariable Long id) {
        return cardSetService.getCardSet(id);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardSetModelDto> updateCardSet(@PathVariable Long id, @RequestBody CardSetRequest request) {
        CardSetRequestDto dto = new CardSetRequestDto(request.name());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardSetService.updateCardSet(id, dto));
    }
}

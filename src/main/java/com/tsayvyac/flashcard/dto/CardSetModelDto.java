package com.tsayvyac.flashcard.dto;

import java.util.Set;

public record CardSetModelDto(
        Long id,
        String name,
        Set<FlashcardModelDto> flashcards
) {}

package com.tsayvyac.flashcard.dto;

import java.util.Set;

public record CardSetDto(
        String name,
        Set<FlashcardDto> flashcards
) {}

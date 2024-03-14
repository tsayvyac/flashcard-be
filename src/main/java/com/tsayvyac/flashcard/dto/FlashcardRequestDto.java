package com.tsayvyac.flashcard.dto;

public record FlashcardRequestDto(
        String front,
        String back,
        Long cardSetId
) {}

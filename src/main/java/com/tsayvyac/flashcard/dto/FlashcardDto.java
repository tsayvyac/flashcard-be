package com.tsayvyac.flashcard.dto;

public record FlashcardDto(
        Long id,
        String front,
        String back,
        Long cardSetId
) {
}

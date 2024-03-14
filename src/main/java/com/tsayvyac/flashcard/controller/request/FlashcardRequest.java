package com.tsayvyac.flashcard.controller.request;

public record FlashcardRequest(
        String front,
        String back,
        Long cardSetId
) {}

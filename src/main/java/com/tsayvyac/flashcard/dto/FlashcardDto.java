package com.tsayvyac.flashcard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record FlashcardDto(
        Long id,
        String front,
        String back,
        Long cardSetId,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate nextDate
) {
}

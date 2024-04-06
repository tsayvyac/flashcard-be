package com.tsayvyac.flashcard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record FlashcardDto(
        Long id,
        String front,
        String back,
        Long cardSetId,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date nextDate
) {
}

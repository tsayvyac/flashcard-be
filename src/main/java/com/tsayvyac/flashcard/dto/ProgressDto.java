package com.tsayvyac.flashcard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ProgressDto(
        Long id,
        Integer repetitions,
        Integer streak,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate nextDate
) {
}

package com.tsayvyac.flashcard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record ProgressDto(
        Long id,
        Integer repetitions,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date nextDate
) {
}

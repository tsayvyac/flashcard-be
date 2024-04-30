package com.tsayvyac.flashcard.dto;

public record CardSetDto(
        Long id,
        String name,
        Integer countRep,
        Integer countAll
) {
}

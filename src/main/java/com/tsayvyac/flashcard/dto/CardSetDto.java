package com.tsayvyac.flashcard.dto;

import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record CardSetDto(
        Long id,
        @Size(min = 2, max = 32, message = "Name of set must contain a minimum of 2 and a maximum of 32 characters")
        String name,
        Integer countRep,
        Integer countAll
) implements Serializable {
}

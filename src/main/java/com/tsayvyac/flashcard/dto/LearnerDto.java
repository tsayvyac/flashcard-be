package com.tsayvyac.flashcard.dto;

import java.io.Serializable;

public record LearnerDto(
        Long id,
        String username,
        String email
) implements Serializable {
}

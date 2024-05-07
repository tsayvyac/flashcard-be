package com.tsayvyac.flashcard.controller.response;

import com.tsayvyac.flashcard.dto.LearnerDto;

public record AuthResponse(
        LearnerDto learner,
        String token
) {
}

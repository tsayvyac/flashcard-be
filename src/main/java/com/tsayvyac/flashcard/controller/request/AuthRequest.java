package com.tsayvyac.flashcard.controller.request;

public record AuthRequest(
        String username,
        String password
) {
}

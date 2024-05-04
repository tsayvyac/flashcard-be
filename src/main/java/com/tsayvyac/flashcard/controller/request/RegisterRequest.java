package com.tsayvyac.flashcard.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        String username,
        @Email(message = "Email should be valid")
        String email,
        @Size(min = 8, max = 64, message = "The password must contain a minimum of 8 and a maximum of 64 characters")
        String password
) {
}

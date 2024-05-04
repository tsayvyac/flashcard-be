package com.tsayvyac.flashcard.service;

import com.tsayvyac.flashcard.controller.request.AuthRequest;
import com.tsayvyac.flashcard.controller.request.RegisterRequest;
import com.tsayvyac.flashcard.controller.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);
}

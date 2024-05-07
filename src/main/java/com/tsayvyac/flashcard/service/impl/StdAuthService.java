package com.tsayvyac.flashcard.service.impl;

import com.tsayvyac.flashcard.controller.request.AuthRequest;
import com.tsayvyac.flashcard.controller.request.RegisterRequest;
import com.tsayvyac.flashcard.controller.response.AuthResponse;
import com.tsayvyac.flashcard.exception.ValidationException;
import com.tsayvyac.flashcard.model.Learner;
import com.tsayvyac.flashcard.repository.LearnerRepository;
import com.tsayvyac.flashcard.service.AuthService;
import com.tsayvyac.flashcard.service.JwtService;
import com.tsayvyac.flashcard.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j(topic = "AUTH_SERVICE")
@Service
@RequiredArgsConstructor
public class StdAuthService implements AuthService {
    private final LearnerRepository learnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (learnerRepository.existsByUsername(request.username()))
            throw new ValidationException("Learner with username " + request.username() + " already exist!");
        if (learnerRepository.existsByEmail(request.email()))
            throw new ValidationException("Learner with email " + request.email() + " already exist!");
        Learner learner = Mapper.requestToLearner(request, passwordEncoder);
        learnerRepository.save(learner);
        String token = jwtService.generateToken(learner);
        log.info("Learner with username {} was successfully registered", learner.getUsername());
        return Mapper.authRequestToResponse(token, learner);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        Learner learner = learnerRepository.findByUsername(request.username()).orElseThrow(() ->
                new ValidationException("Username does not exist"));

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        String token = jwtService.generateToken(learner);
        log.info("Learner with username {} was successfully authenticated", learner.getUsername());
        return Mapper.authRequestToResponse(token, learner);
    }
}

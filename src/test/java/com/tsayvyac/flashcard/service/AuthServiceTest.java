package com.tsayvyac.flashcard.service;

import com.tsayvyac.flashcard.controller.request.AuthRequest;
import com.tsayvyac.flashcard.controller.request.RegisterRequest;
import com.tsayvyac.flashcard.controller.response.AuthResponse;
import com.tsayvyac.flashcard.model.Learner;
import com.tsayvyac.flashcard.repository.LearnerRepository;
import com.tsayvyac.flashcard.service.impl.StdAuthService;
import com.tsayvyac.flashcard.util.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private LearnerRepository learnerRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private StdAuthService authService;

    @Test
    void registerLearner_SuccessfullyRegistered_ReturnAuthResponse() {
        RegisterRequest request = new RegisterRequest("tsayvyac", "email@mail.com", "12345qwerty");
        Learner learner = Mapper.requestToLearner(request, passwordEncoder);

        when(learnerRepository.existsByEmail(request.email())).thenReturn(false);
        when(learnerRepository.existsByUsername(request.username())).thenReturn(false);
        when(learnerRepository.save(Mockito.any(Learner.class))).thenReturn(learner);
        when(jwtService.generateToken(Mockito.any())).thenReturn("token");

        AuthResponse authResponse = authService.register(request);

        Assertions.assertNotNull(authResponse);
        Assertions.assertEquals(authResponse.learner().username(), request.username());
    }

    @Test
    void authenticate_SuccessfullyAuthenticated_ReturnAuthResponse() {
        AuthRequest request = new AuthRequest("tsayvyac", "12345qwerty");
        Learner learner = Learner.builder()
                .username("tsayvyac")
                .password("12345qwerty")
                .build();

        when(learnerRepository.findByUsername(request.username())).thenReturn(Optional.of(learner));
        when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(learner.getUsername(), learner.getPassword()));
        when(jwtService.generateToken(learner)).thenReturn("token");

        AuthResponse authResponse = authService.authenticate(request);

        Assertions.assertNotNull(authResponse);
        Assertions.assertEquals(authResponse.learner().username(), request.username());
    }
}

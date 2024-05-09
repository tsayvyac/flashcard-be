package com.tsayvyac.flashcard.service.impl;

import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;
import com.tsayvyac.flashcard.model.Learner;
import com.tsayvyac.flashcard.model.Progress;
import com.tsayvyac.flashcard.repository.CardSetRepository;
import com.tsayvyac.flashcard.repository.FlashcardRepository;
import com.tsayvyac.flashcard.util.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlashcardServiceTest {
    @Mock
    private FlashcardRepository flashcardRepository;
    @Mock
    private CardSetRepository cardSetRepository;
    @Mock
    private AsyncService asyncService;

    @InjectMocks
    private StdFlashcardService flashcardService;
    private static Learner learner;

    @BeforeAll
    static void setUp() {
        mockStatic(SecurityContextHolder.class);
        mockStatic(Mapper.class);
    }

    @BeforeEach
    void authenticate() {
        learner = Learner.builder()
                .username("tsayvyac")
                .password("12345qwerty")
                .build();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
    }

    @Test
    void createFlashcard_SuccessfullyCreated_ReturnSavedFlashcardDto() {
        CardSet cardSet = CardSet.builder()
                .id(1L)
                .name("Test set")
                .build();
        Flashcard flashcard = Flashcard.builder()
                .id(1L)
                .front("Front text")
                .back("Back text")
                .cardSet(cardSet)
                .build();
        FlashcardDto flashcardDto = new FlashcardDto(1L, "Front text", "Back text", 1L, LocalDate.now());
        Progress progress = Progress.builder()
                .repetitions(0)
                .streak(0)
                .nextDate(LocalDate.of(2001, Month.MAY, 23))
                .learner(learner)
                .flashcard(flashcard)
                .build();
        flashcard.setProgress(progress);
        when(Mapper.dtoToFlashcard(flashcardDto, cardSet)).thenReturn(flashcard);
        when(cardSetRepository.findById(1L)).thenReturn(Optional.of(cardSet));
        when(flashcardRepository.save(Mockito.any())).thenReturn(flashcard);
        when(Mapper.flashcardToDto(flashcard, cardSet.getId())).thenReturn(flashcardDto);

        FlashcardDto result = flashcardService.createFlashcard(flashcardDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(flashcardDto.front(), result.front());
    }

    @Test
    void getFlashcardById_SuccessfullyRetrieved_ReturnFlashcardDto() {
        CardSet cardSet = CardSet.builder().id(1L).build();
        Flashcard flashcard = Flashcard.builder()
                .id(1L)
                .front("Front text")
                .back("Back text")
                .cardSet(cardSet)
                .build();
        FlashcardDto flashcardDto = new FlashcardDto(1L, "Front text", "Back text", 1L, LocalDate.now());
        when(flashcardRepository.findById(flashcard.getId())).thenReturn(Optional.of(flashcard));
        when(Mapper.flashcardToDto(flashcard, flashcard.getCardSet().getId())).thenReturn(flashcardDto);

        FlashcardDto result = flashcardService.getFlashcardById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(flashcardDto.front(), flashcard.getFront());
    }

    @Test
    void updateFlashcardFrontAndBack_FrontsAndBacksAreEqual_ReturnUpdatedFlashcardDto() {
        CardSet cardSet = CardSet.builder().id(1L).build();
        Flashcard exising = Flashcard.builder()
                .id(1L)
                .front("Front text")
                .back("Back text")
                .cardSet(cardSet)
                .build();
        FlashcardDto incompleteDto = new FlashcardDto(1L, "Information", "Wise", 1L,LocalDate.now());
        Flashcard incomplete = Flashcard.builder()
                .id(1L)
                .front(incompleteDto.front())
                .back(incompleteDto.back())
                .cardSet(cardSet)
                .build();
        when(flashcardRepository.findById(exising.getId())).thenReturn(Optional.of(exising));
        when(cardSetRepository.findById(cardSet.getId())).thenReturn(Optional.of(cardSet));
        when(Mapper.dtoToFlashcard(incompleteDto, cardSet)).thenReturn(incomplete);
        when(Mapper.flashcardToDto(exising, exising.getCardSet().getId())).thenReturn(incompleteDto);

        FlashcardDto result = flashcardService.updateFlashcard(1L, incompleteDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.front(), exising.getFront());
        Assertions.assertEquals(result.back(), exising.getBack());
    }

    @Test
    void checkAsyncExecution_TriggerWithCorrectParameters_ReturnVoid() {
        Long id = 1L;
        int score = 1;
        flashcardService.updateProgress(id, score);

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Integer> scoreCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(asyncService).updateProgress(idCaptor.capture(), scoreCaptor.capture());

        Assertions.assertEquals(id, idCaptor.getValue());
        Assertions.assertEquals(score, scoreCaptor.getValue());
    }

}

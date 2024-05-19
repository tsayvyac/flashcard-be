package com.tsayvyac.flashcard.service.impl;

import com.tsayvyac.flashcard.dto.CardSetDto;
import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.dto.PageDto;
import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;
import com.tsayvyac.flashcard.model.Learner;
import com.tsayvyac.flashcard.model.Progress;
import com.tsayvyac.flashcard.repository.CardSetRepository;
import com.tsayvyac.flashcard.repository.FlashcardRepository;
import com.tsayvyac.flashcard.util.Mapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardSetServiceTest {
    @Mock
    private CardSetRepository cardSetRepository;
    @Mock
    private FlashcardRepository flashcardRepository;

    @InjectMocks
    private StdCardSetService cardSetService;
    private static Learner learner;
    private static List<CardSet> cardSetList;
    private static MockedStatic<SecurityContextHolder> mockedSec;
    private static MockedStatic<Mapper> mockedMap;

    @BeforeAll
    static void setUp() {
        mockedSec = mockStatic(SecurityContextHolder.class);
        mockedMap = mockStatic(Mapper.class);
        cardSetList = new ArrayList<>();
        CardSet cardSet1 = CardSet.builder()
                .name("Test set 1")
                .learner(learner)
                .build();
        CardSet cardSet2 = CardSet.builder()
                .name("Test set 2")
                .learner(learner)
                .build();
        cardSetList.add(cardSet1);
        cardSetList.add(cardSet2);
    }

    @BeforeEach
    void authenticate() {
        learner = Learner.builder()
                .username("tsayvyac")
                .password("12345qwerty")
                .build();
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(learner);
    }

    @AfterAll
    static void close() {
        mockedSec.close();
        mockedMap.close();
    }

    @Test
    void createCardSet_SuccessfullyCreated_ReturnCardSetDto() {
        CardSet cardSet = CardSet.builder()
                .name("Test set")
                .learner(learner)
                .build();
        CardSetDto cardSetDto = new CardSetDto(1L, cardSet.getName(), 0, 0);
        when(cardSetRepository.save(Mockito.any())).thenReturn(cardSet);

        CardSetDto result = cardSetService.createCardSet(cardSetDto);

        Assertions.assertNotNull(result);
    }

    @Test
    void getCardSets_SuccessfullyRetrieved_ReturnPageDto() {
        Page<CardSet> mockedPage = mock(Page.class);
        when(mockedPage.getContent()).thenReturn(cardSetList);
        when(cardSetRepository.findAllByLearner(any(), any())).thenReturn(mockedPage);
        when(Mapper.cardSetToDto(any(CardSet.class), anyInt(), anyInt()))
                .thenAnswer(invocation -> {
                    CardSet cardSet = invocation.getArgument(0);
                    return new CardSetDto(cardSet.getId(), cardSet.getName(), 0, 0); // Simplified mapping for this example
                });

        when(Mapper.mapToPageDto(anyList(), anyInt(), anyInt(), any(Page.class)))
                .thenAnswer(invocation -> {
                    List<CardSetDto> listOfDto = invocation.getArgument(0);
                    int pageNo = invocation.getArgument(1);
                    int pageSize = invocation.getArgument(2);
                    Page<CardSet> page = invocation.getArgument(3);
                    return new PageDto<>(listOfDto, pageNo, pageSize, page.getTotalElements(), page.getTotalPages(), page.isLast());
                });

        PageDto<CardSetDto> pageDto = cardSetService.getCardSets(0, 10, false);

        Assertions.assertEquals(2, pageDto.list().size());
    }

    @Test
    void getSetsInfo_SuccessfullyRetrieved_ReturnListOfCardSetDto() {
        when(cardSetRepository.findAllByLearner(learner)).thenReturn(cardSetList);

        List<CardSetDto> cardSetDtoList = cardSetService.getSetsInfo();

        Assertions.assertEquals(cardSetList.size(), cardSetDtoList.size());
    }

    @Test
    void getFlashcardsInCardSet_SuccessfullyRetrieved_ReturnPageDto() {
        CardSet cardSet = cardSetList.get(0);
        List<Flashcard> flashcardList = new ArrayList<>();
        Flashcard flashcard1 = Flashcard.builder()
                .progress(Progress.builder().build())
                .build();
        Flashcard flashcard2 = Flashcard.builder()
                .progress(Progress.builder().build())
                .build();
        flashcardList.add(flashcard1);
        flashcardList.add(flashcard2);
        Page<Flashcard> mockedPage = mock(Page.class);

        when(cardSetRepository.findByIdAndLearner(1L, learner)).thenReturn(Optional.of(cardSet));
        when(mockedPage.getContent()).thenReturn(flashcardList);
        when(flashcardRepository.findAllPagesByCardSet(cardSet, PageRequest.of(0, 10))).thenReturn(mockedPage);
        when(Mapper.flashcardToDto(any(Flashcard.class), eq(1L)))
                .thenAnswer(invocation -> {
                    Flashcard flashcard = invocation.getArgument(0);
                    Long cardSetId = invocation.getArgument(1);
                    return new FlashcardDto(flashcard.getId(), flashcard.getFront(), flashcard.getBack(), cardSetId, flashcard.getProgress().getNextDate());
                });

        when(Mapper.mapToPageDto(anyList(), anyInt(), anyInt(), any(Page.class)))
                .thenAnswer(invocation -> {
                    List<FlashcardDto> listOfDto = invocation.getArgument(0);
                    int pageNo = invocation.getArgument(1);
                    int pageSize = invocation.getArgument(2);
                    Page<Flashcard> page = invocation.getArgument(3);
                    return new PageDto<>(listOfDto, pageNo, pageSize, page.getTotalElements(), page.getTotalPages(), page.isLast());
                });

        PageDto<FlashcardDto> flashcardDtoPageDto = cardSetService.getFlashcardsInSet(1L, 0, 10);

        Assertions.assertEquals(2, flashcardDtoPageDto.list().size());
    }

    @Test
    void updateNameOfCardSet_NamesAreEqual_ReturnUpdatedCardSet() {
        CardSet existing = CardSet.builder().id(1L).name("Existing").build();
        CardSetDto updatedDto = new CardSetDto(2L, "Updated", 0, 0);
        CardSet updated = CardSet.builder().id(2L).name("Updated").build();

        when(cardSetRepository.findByIdAndLearner(1L, learner)).thenReturn(Optional.of(existing));
        when(Mapper.dtoToCardSet(updatedDto, learner)).thenReturn(updated);
        when(Mapper.cardSetToDto(existing, 0, 0)).thenReturn(updatedDto);

        CardSetDto updateCardSetDto = cardSetService.updateCardSet(1L, updatedDto);

        Assertions.assertEquals(existing.getName(), updateCardSetDto.name());
    }

}

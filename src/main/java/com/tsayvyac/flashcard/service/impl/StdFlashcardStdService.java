package com.tsayvyac.flashcard.service.impl;

import com.tsayvyac.flashcard.dto.FlashcardModelDto;
import com.tsayvyac.flashcard.dto.FlashcardRequestDto;
import com.tsayvyac.flashcard.exception.CardSetNotFound;
import com.tsayvyac.flashcard.exception.FlashcardNotFound;
import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;
import com.tsayvyac.flashcard.model.Progress;
import com.tsayvyac.flashcard.repository.CardSetRepository;
import com.tsayvyac.flashcard.repository.FlashcardRepository;
import com.tsayvyac.flashcard.repository.ProgressRepository;
import com.tsayvyac.flashcard.service.FlashcardService;
import com.tsayvyac.flashcard.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
class StdFlashcardStdService implements FlashcardService {
    private final FlashcardRepository flashcardRepository;
    private final CardSetRepository cardSetRepository;
    private final ProgressRepository progressRepository;

    @Override
    public FlashcardModelDto createFlashcard(FlashcardRequestDto dto) {
        Flashcard flashcard = flashcardRepository.save(Flashcard.builder()
                .front(dto.front())
                .back(dto.back())
                .build()
        );

        Progress progress = Progress.builder()
                .repetitions(0)
                .nextDate(new Date(Long.MIN_VALUE))
                .flashcard(flashcard)
                .build();
        progressRepository.save(progress);

        CardSet cardSet = cardSetRepository.findById(dto.cardSetId())
                .orElseThrow(() -> new CardSetNotFound("Card set with id " + dto.cardSetId() + " not found!"));
        cardSet.getFlashcards().add(flashcard);
        cardSetRepository.save(cardSet);

        return Mapper.flashcardToDto(flashcard);
    }

    @Override
    public FlashcardModelDto getFlashcard(Long id) {
        return Mapper.flashcardToDto(getFlashcardById(id));
    }

    @Override
    public void deleteFlashcard(Long id) {
        Flashcard flashcard = getFlashcardById(id);
        flashcardRepository.delete(flashcard);
    }

    private Flashcard getFlashcardById(Long id) {
        return flashcardRepository.findById(id)
                .orElseThrow(() -> new FlashcardNotFound("Flashcard with id " + id + " not found!"));
    }

}

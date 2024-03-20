package com.tsayvyac.flashcard.service.impl;

import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.dto.ProgressDto;
import com.tsayvyac.flashcard.exception.NotFoundException;
import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;
import com.tsayvyac.flashcard.model.Progress;
import com.tsayvyac.flashcard.repository.CardSetRepository;
import com.tsayvyac.flashcard.repository.FlashcardRepository;
import com.tsayvyac.flashcard.repository.ProgressRepository;
import com.tsayvyac.flashcard.service.FlashcardService;
import com.tsayvyac.flashcard.util.Mapper;
import com.tsayvyac.flashcard.util.Patcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.tsayvyac.flashcard.util.Constant.Str.NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
class StdFlashcardStdService implements FlashcardService {
    private final FlashcardRepository flashcardRepository;
    private final CardSetRepository cardSetRepository;
    private final ProgressRepository progressRepository;

    @Override
    public FlashcardDto createFlashcard(FlashcardDto dto) {
        CardSet cardSet = getCardSetById(dto.cardSetId());
        Flashcard flashcard = Mapper.dtoToFlashcard(dto, cardSet);
        Progress progress = Progress.builder()
                .repetitions(0)
                .nextDate(new Date(0))
                .flashcard(flashcard)
                .build();
        flashcard.setProgress(progress);

        flashcardRepository.save(flashcard);
        return Mapper.flashcardToDto(flashcard, cardSet.getId());
    }

    @Override
    public FlashcardDto getFlashcardById(Long id) {
        Flashcard flashcard = getById(id);

        return Mapper.flashcardToDto(flashcard, flashcard.getCardSet().getId());
    }

    // TODO: Logic if flashcard is moved to another card set
    @Override
    public FlashcardDto updateFlashcard(Long id, FlashcardDto dto) {
        CardSet cardSet = getCardSetById(dto.cardSetId());
        Flashcard existing = getById(id);
        Flashcard incomplete = Mapper.dtoToFlashcard(dto, cardSet);
        try {
            Patcher.patchUpdate(existing, incomplete);
            flashcardRepository.save(existing);
        } catch (IllegalAccessException ex) {
            log.error(ex.getMessage());
        }

        return Mapper.flashcardToDto(existing, existing.getCardSet().getId());
    }

    @Override
    public void deleteFlashcard(Long id) {
        flashcardRepository.delete(getById(id));
    }

    @Override
    public ProgressDto getProgress(Long id) {
        Progress progress = getProgressById(id);

        return Mapper.progressToDto(progress);
    }

    @Override
    public ProgressDto updateProgress(Long id, ProgressDto dto) {
        Progress existing = getProgressById(id);
        Progress incomplete = Mapper.dtoToProgress(dto);
        try {
            Patcher.patchUpdate(existing, incomplete);
            progressRepository.save(existing);
        } catch (IllegalAccessException ex) {
            log.error(ex.getMessage());
        }

        return Mapper.progressToDto(existing);
    }

    private Flashcard getById(Long id) {
        return flashcardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flashcard with id " + id + NOT_FOUND));
    }

    private CardSet getCardSetById(Long id) {
        return cardSetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Card set with id " + id + NOT_FOUND));
    }

    private Progress getProgressById(Long id) {
        return progressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Progress for flashcard with id " + id + NOT_FOUND));
    }
}

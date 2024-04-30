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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;

import static com.tsayvyac.flashcard.util.Constant.Str.NOT_FOUND;

@Slf4j(topic = "FLASHCARD_SERVICE")
@Service
@RequiredArgsConstructor
class StdFlashcardStdService implements FlashcardService {
    private final AsyncService asyncService;
    private final FlashcardRepository flashcardRepository;
    private final CardSetRepository cardSetRepository;
    private final ProgressRepository progressRepository;

    @Override
    @Transactional
    public FlashcardDto createFlashcard(FlashcardDto dto) {
        CardSet cardSet = getCardSetById(dto.cardSetId());
        Flashcard flashcard = Mapper.dtoToFlashcard(dto, cardSet);
        Progress progress = Progress.builder()
                .repetitions(0)
                .streak(0)
                .nextDate(LocalDate.of(2001, Month.MAY, 23))
                .flashcard(flashcard)
                .build();
        flashcard.setProgress(progress);

        Flashcard saved = flashcardRepository.save(flashcard);
        log.info("Flashcard with id {} was saved successfully!", saved.getId());
        return Mapper.flashcardToDto(saved, cardSet.getId());
    }

    @Override
    public FlashcardDto getFlashcardById(Long id) {
        Flashcard flashcard = getById(id);

        return Mapper.flashcardToDto(flashcard, flashcard.getCardSet().getId());
    }

    @Override
    @Transactional
    public FlashcardDto updateFlashcard(Long id, FlashcardDto dto) {
        Flashcard existing = getById(id);
        CardSet cardSet = dto.cardSetId() != null
                ? getCardSetById(dto.cardSetId())
                : existing.getCardSet();
        Flashcard incomplete = Mapper.dtoToFlashcard(dto, cardSet);
        try {
            Patcher.patchUpdate(existing, incomplete);
            flashcardRepository.save(existing);
        } catch (IllegalAccessException ex) {
            log.error(ex.getMessage());
        }
        log.info("Flashcard with id {} was updated!", id);

        return Mapper.flashcardToDto(existing, existing.getCardSet().getId());
    }

    @Override
    public void deleteFlashcard(Long id) {
        flashcardRepository.delete(getById(id));
        log.info("Flashcard with id {} was deleted!", id);
    }

    @Override
    public ProgressDto getProgress(Long id) {
        Progress progress = getProgressById(id);

        return Mapper.progressToDto(progress);
    }

    @Override
    public void updateProgress(Long id, int score) {
        asyncService.updateProgress(id, score);
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

package com.tsayvyac.flashcard.service.impl;

import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.exception.NotFoundException;
import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;
import com.tsayvyac.flashcard.model.Progress;
import com.tsayvyac.flashcard.repository.CardSetRepository;
import com.tsayvyac.flashcard.repository.FlashcardRepository;
import com.tsayvyac.flashcard.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
class StdFlashcardStdService implements FlashcardService {
    private final FlashcardRepository flashcardRepository;
    private final CardSetRepository cardSetRepository;

    @Override
    public FlashcardDto createFlashcard(FlashcardDto dto) {
        CardSet cardSet = cardSetRepository.findById(dto.cardSetId())
                .orElseThrow(() -> new NotFoundException("Card set with id " + dto.cardSetId() + " not found!"));
        Flashcard flashcard = Flashcard.builder()
                .id(dto.id())
                .front(dto.front())
                .back(dto.back())
                .cardSet(cardSet)
                .build();
        Progress progress = Progress.builder()
                .repetitions(0)
                .nextDate(new Date(0))
                .flashcard(flashcard)
                .build();
        flashcard.setProgress(progress);

        flashcardRepository.save(flashcard);
        return new FlashcardDto(flashcard.getId(), flashcard.getFront(), flashcard.getBack(), cardSet.getId());
    }

    @Override
    public FlashcardDto getFlashcardById(Long id) {
        Flashcard flashcard = getById(id);

        return new FlashcardDto(flashcard.getId(), flashcard.getFront(), flashcard.getBack(), flashcard.getCardSet().getId());
    }

    @Override
    public void deleteFlashcard(Long id) {
        flashcardRepository.delete(getById(id));
    }

    private Flashcard getById(Long id) {
        return flashcardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flashcard with id " + id + " not found!"));
    }
}

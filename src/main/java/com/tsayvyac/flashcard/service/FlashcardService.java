package com.tsayvyac.flashcard.service;

import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.dto.ProgressDto;

public interface FlashcardService {
    FlashcardDto createFlashcard(FlashcardDto dto);
    FlashcardDto getFlashcardById(Long id);
    FlashcardDto updateFlashcard(Long id, FlashcardDto dto);
    void deleteFlashcard(Long id);
    ProgressDto getProgress(Long id);
    ProgressDto updateProgress(Long id, ProgressDto dto);
}

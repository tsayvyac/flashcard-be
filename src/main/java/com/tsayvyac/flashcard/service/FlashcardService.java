package com.tsayvyac.flashcard.service;

import com.tsayvyac.flashcard.dto.FlashcardModelDto;
import com.tsayvyac.flashcard.dto.FlashcardRequestDto;
import com.tsayvyac.flashcard.model.Flashcard;

public interface FlashcardService {
    FlashcardModelDto createFlashcard(FlashcardRequestDto dto);
    FlashcardModelDto getFlashcard(Long id);
    void deleteFlashcard(Long id);
}

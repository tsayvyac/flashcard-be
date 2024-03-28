package com.tsayvyac.flashcard.service;

import com.tsayvyac.flashcard.dto.CardSetDto;
import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.dto.PageDto;

import java.util.List;

public interface CardSetService {
    CardSetDto createCardSet(CardSetDto dto);
    PageDto<CardSetDto> getCardSets(int pageNo, int pageSize);
    CardSetDto getCardSetById(Long id);
    PageDto<FlashcardDto> getFlashcardsInSet(Long id, int pageNo, int pageSize);
    List<FlashcardDto> getRepetitionFlashcards(Long id);
    CardSetDto updateCardSet(Long id, CardSetDto dto);
    void deleteCardSet(Long id);
}

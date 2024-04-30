package com.tsayvyac.flashcard.service;

import com.tsayvyac.flashcard.dto.CardSetDto;
import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.dto.PageDto;
import com.tsayvyac.flashcard.dto.SetsInfoDto;

import java.util.List;

public interface CardSetService {
    CardSetDto createCardSet(CardSetDto dto);
    PageDto<CardSetDto> getCardSets(int pageNo, int pageSize, boolean isOnlyRep);
    CardSetDto getCardSetById(Long id);
    List<SetsInfoDto> getSetsInfo();
    PageDto<FlashcardDto> getFlashcardsInSet(Long id, int pageNo, int pageSize);
    List<FlashcardDto> getFlashcardsFromSet(Long id, boolean isCram);
    CardSetDto updateCardSet(Long id, CardSetDto dto);
    void deleteCardSet(Long id);
}

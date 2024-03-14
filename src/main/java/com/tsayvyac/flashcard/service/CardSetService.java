package com.tsayvyac.flashcard.service;

import com.tsayvyac.flashcard.dto.CardSetModelDto;
import com.tsayvyac.flashcard.dto.CardSetRequestDto;
import com.tsayvyac.flashcard.model.CardSet;

public interface CardSetService {
    CardSetModelDto createCardSet(CardSetModelDto dto);
    CardSetModelDto getCardSet(Long id);
    CardSetModelDto updateCardSet(Long id, CardSetRequestDto dto);
}

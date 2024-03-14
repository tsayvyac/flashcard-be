package com.tsayvyac.flashcard.util;

import com.tsayvyac.flashcard.dto.CardSetModelDto;
import com.tsayvyac.flashcard.dto.CardSetRequestDto;
import com.tsayvyac.flashcard.dto.FlashcardModelDto;
import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;

import java.util.stream.Collectors;

public class Mapper {

    private Mapper() {}

    public static CardSetModelDto cardSetToDto(CardSet cardSet) {
        return new CardSetModelDto(
                cardSet.getId(),
                cardSet.getName(),
                cardSet.getFlashcards()
                        .stream()
                        .map(Mapper::flashcardToDto)
                        .collect(Collectors.toSet())
        );
    }

    public static CardSet requestDtoToCardSet(CardSetRequestDto dto) {
        return CardSet.builder()
                .name(dto.name())
                .build();
    }

    public static FlashcardModelDto flashcardToDto(Flashcard flashcard) {
        return new FlashcardModelDto(
                flashcard.getId(),
                flashcard.getFront(),
                flashcard.getBack()
        );
    }
}

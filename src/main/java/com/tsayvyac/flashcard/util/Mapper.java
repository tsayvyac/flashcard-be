package com.tsayvyac.flashcard.util;

import com.tsayvyac.flashcard.dto.CardSetDto;
import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;

public class Mapper {

    private Mapper() {}

    public static CardSetDto cardSetToDto(CardSet cardSet) {
        return new CardSetDto(
                cardSet.getId(),
                cardSet.getName()
        );
    }

    public static FlashcardDto flashcardToDto(Flashcard flashcard, Long cardSetId) {
        return new FlashcardDto(
                flashcard.getId(),
                flashcard.getFront(),
                flashcard.getBack(),
                cardSetId
        );
    }
}

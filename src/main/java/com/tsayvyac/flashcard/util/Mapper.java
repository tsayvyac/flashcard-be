package com.tsayvyac.flashcard.util;

import com.tsayvyac.flashcard.dto.CardSetDto;
import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.dto.PageDto;
import com.tsayvyac.flashcard.dto.ProgressDto;
import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;
import com.tsayvyac.flashcard.model.Progress;
import org.springframework.data.domain.Page;

import java.util.List;

public class Mapper {

    private Mapper() {}

    public static <T, K> PageDto<T> mapToPageDto(List<T> listOfDto, int pageNo, int pageSize, Page<K> p) {
        return new PageDto<>(
                listOfDto,
                pageNo,
                pageSize,
                p.getTotalElements(),
                p.getTotalPages(),
                p.isLast()
        );
    }

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

    public static CardSet dtoToCardSet(CardSetDto dto) {
        return CardSet.builder()
                .id(dto.id())
                .name(dto.name())
                .build();
    }

    public static Flashcard dtoToFlashcard(FlashcardDto dto, CardSet cardSet) {
        return Flashcard.builder()
                .id(dto.id())
                .front(dto.front())
                .back(dto.back())
                .cardSet(cardSet)
                .build();
    }

    public static ProgressDto progressToDto(Progress progress) {
        return new ProgressDto(
                progress.getId(),
                progress.getRepetitions(),
                progress.getNextDate()
        );
    }

    public static Progress dtoToProgress(ProgressDto dto) {
        return Progress.builder()
                .id(dto.id())
                .repetitions(dto.repetitions())
                .nextDate(dto.nextDate())
                .build();
    }
}

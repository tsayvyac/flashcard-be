package com.tsayvyac.flashcard.util;

import com.tsayvyac.flashcard.controller.request.RegisterRequest;
import com.tsayvyac.flashcard.dto.*;
import com.tsayvyac.flashcard.model.*;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public static CardSetDto cardSetToDto(CardSet cardSet, Integer countRep, Integer countAll) {
        return new CardSetDto(
                cardSet.getId(),
                cardSet.getName(),
                countRep,
                countAll
        );
    }

    public static FlashcardDto flashcardToDto(Flashcard flashcard, Long cardSetId) {
        return new FlashcardDto(
                flashcard.getId(),
                flashcard.getFront(),
                flashcard.getBack(),
                cardSetId,
                flashcard.getProgress().getNextDate()
        );
    }

    public static CardSet dtoToCardSet(CardSetDto dto, Learner learner) {
        return CardSet.builder()
                .id(dto.id())
                .name(dto.name())
                .learner(learner)
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
                progress.getStreak(),
                progress.getNextDate()
        );
    }

    public static LearnerDto learnerToDto(Learner learner) {
        return new LearnerDto(
                learner.getUid(),
                learner.getUsername(),
                learner.getEmail()
        );
    }

    public static Learner requestToLearner(RegisterRequest request, PasswordEncoder encoder) {
        return Learner.builder()
                .username(request.username())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .role(Role.ROLE_USER)
                .build();
    }
}

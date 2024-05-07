package com.tsayvyac.flashcard.service.impl.strategy;

import com.tsayvyac.flashcard.model.Progress;

import java.time.LocalDate;

public class MidScoreStrategy implements ComputeProgressStrategy {

    @Override
    public Progress computeDate(Progress existing) {
        int newStreak = existing.getStreak() / 2;
        LocalDate nextD = LocalDate.now().plusDays(newStreak);

        return Progress.builder()
                .repetitions(existing.getRepetitions() + 1)
                .streak(newStreak)
                .lastScore(0)
                .nextDate(nextD)
                .build();
    }
}

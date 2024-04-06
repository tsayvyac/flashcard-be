package com.tsayvyac.flashcard.service.impl.strategy;

import com.tsayvyac.flashcard.model.Progress;

import java.util.Date;

public class BadScoreStrategy implements ComputeProgressStrategy {

    @Override
    public Progress computeDate(Progress existing) {
        return Progress.builder()
                .repetitions(existing.getRepetitions() + 1)
                .streak(0)
                .nextDate(new Date())
                .build();
    }
}

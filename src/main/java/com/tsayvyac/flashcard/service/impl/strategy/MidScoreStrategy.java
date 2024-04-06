package com.tsayvyac.flashcard.service.impl.strategy;

import com.tsayvyac.flashcard.model.Progress;

import java.util.Calendar;
import java.util.Date;

public class MidScoreStrategy implements ComputeProgressStrategy {

    @Override
    public Progress computeDate(Progress existing) {
        int newStreak = existing.getStreak() / 2;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, newStreak);
        Date nextD = calendar.getTime();

        return Progress.builder()
                .repetitions(existing.getRepetitions() + 1)
                .streak(newStreak)
                .nextDate(nextD)
                .build();
    }
}

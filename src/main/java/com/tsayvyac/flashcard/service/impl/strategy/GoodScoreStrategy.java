package com.tsayvyac.flashcard.service.impl.strategy;

import com.tsayvyac.flashcard.model.Progress;

import java.util.Calendar;
import java.util.Date;

import static com.tsayvyac.flashcard.util.Constant.*;

public class GoodScoreStrategy implements ComputeProgressStrategy {

    @Override
    public Progress computeDate(Progress existing) {
        int daysToAdd = (int) (Math.max(1, AFTER_DAYS * existing.getStreak() * REPETITION_COEFFICIENT));
        if (daysToAdd > MAX_DAYS) daysToAdd = MAX_DAYS;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, daysToAdd);
        Date nextD = calendar.getTime();

        return Progress.builder()
                .repetitions(existing.getRepetitions() + 1)
                .streak(existing.getStreak() + 1)
                .nextDate(nextD)
                .build();
    }
}

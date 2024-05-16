package com.tsayvyac.flashcard.service.impl.strategy;

import com.tsayvyac.flashcard.model.Progress;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j(topic = "GOOD_SCORE_LOG")
public class GoodScoreStrategy implements ComputeProgressStrategy {

    @Override
    public Progress computeDate(Progress existing) {
        int daysToAdd = numberFromFibByPos(existing.getStreak() + 1);
        LocalDate nextD = LocalDate.now().plusDays(daysToAdd);
        log.info("Days to add: {}", daysToAdd);
        return Progress.builder()
                .repetitions(existing.getRepetitions() + 1)
                .streak(existing.getStreak() + 1)
                .lastScore(1)
                .nextDate(nextD)
                .build();
    }

    private int numberFromFibByPos(int pos) {
        if (pos == 1 || pos == 0) return 1;
        if (pos >= 9) return 34;
        else {
            int prev = 0;
            int current = 1;
            for (int i = 2; i <= pos; i++) {
                int temp = current;
                current += prev;
                prev = temp;
            }
            return current;
        }
    }
}

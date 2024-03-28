package com.tsayvyac.flashcard.service.impl.strategy;

import com.tsayvyac.flashcard.model.Progress;

public class ContextStrategy {
    private ComputeProgressStrategy strategy;

    public Progress computeDate(Progress existing, int score) {
        setStrategy(score);
        return strategy.computeDate(existing);
    }

    private void setStrategy(int score) {
        if (score == -1) strategy = new BadScoreStrategy();
        else if (score == 0) strategy = new MidScoreStrategy();
        else if (score == 1) strategy = new GoodScoreStrategy();
    }
}

package com.tsayvyac.flashcard.service.impl.strategy;

import com.tsayvyac.flashcard.model.Progress;

public interface ComputeProgressStrategy {
    Progress computeDate(Progress existing);
}

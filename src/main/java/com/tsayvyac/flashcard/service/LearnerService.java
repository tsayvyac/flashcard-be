package com.tsayvyac.flashcard.service;

import com.tsayvyac.flashcard.dto.LearnerDto;

public interface LearnerService {
    LearnerDto getLearnerInfo();
    Integer getStats();
}

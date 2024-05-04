package com.tsayvyac.flashcard.service.impl;

import com.tsayvyac.flashcard.dto.LearnerDto;
import com.tsayvyac.flashcard.model.Learner;
import com.tsayvyac.flashcard.service.LearnerService;
import com.tsayvyac.flashcard.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j(topic = "LEARNER_SERVICE")
@Service
@RequiredArgsConstructor
class StdLearnerStdService implements LearnerService {

    @Override
    public LearnerDto getLearnerInfo() {
        Learner learner = getAuthenticated();
        return Mapper.learnerToDto(learner);
    }

    private Learner getAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Learner) authentication.getPrincipal();
    }
}

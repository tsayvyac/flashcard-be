package com.tsayvyac.flashcard.controller;

import com.tsayvyac.flashcard.controller.response.StatsResponse;
import com.tsayvyac.flashcard.dto.LearnerDto;
import com.tsayvyac.flashcard.service.LearnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learners")
@RequiredArgsConstructor
public class LearnerController {
    private final LearnerService learnerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public LearnerDto getLearnerInfo() {
        return learnerService.getLearnerInfo();
    }

    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StatsResponse getLearnerStats() {
        return new StatsResponse(learnerService.getStats());
    }
}

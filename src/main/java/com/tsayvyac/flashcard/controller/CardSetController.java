package com.tsayvyac.flashcard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sets")
@RequiredArgsConstructor
public class CardSetController {

    @GetMapping
    public String hello() {
        return "Hello";
    }
}

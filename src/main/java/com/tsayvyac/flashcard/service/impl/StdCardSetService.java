package com.tsayvyac.flashcard.service.impl;

import com.tsayvyac.flashcard.dto.CardSetDto;
import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.dto.PageDto;
import com.tsayvyac.flashcard.exception.NotFoundException;
import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;
import com.tsayvyac.flashcard.repository.CardSetRepository;
import com.tsayvyac.flashcard.repository.FlashcardRepository;
import com.tsayvyac.flashcard.service.CardSetService;
import com.tsayvyac.flashcard.util.Mapper;
import com.tsayvyac.flashcard.util.Patcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class StdCardSetService implements CardSetService {
    private final CardSetRepository cardSetRepository;
    private final FlashcardRepository flashcardRepository;

    @Override
    public CardSetDto createCardSet(CardSetDto dto) {
        CardSet cardSet = cardSetRepository.save(CardSet.builder()
                .id(dto.id())
                .name(dto.name())
                .build()
        );

        return new CardSetDto(cardSet.getId(), cardSet.getName());
    }

    @Override
    public PageDto<CardSetDto> getCardSets(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CardSet> cardSets = cardSetRepository.findAll(pageable);
        List<CardSet> listOfCardSets = cardSets.getContent();
        List<CardSetDto> listOfDto = listOfCardSets.stream().map(Mapper::cardSetToDto).toList();

        return new PageDto<>(
                listOfDto,
                pageNo,
                pageSize,
                cardSets.getTotalElements(),
                cardSets.getTotalPages(),
                cardSets.isLast()
        );
    }

    @Override
    public CardSetDto getCardSetById(Long id) {
        CardSet cardSet = getById(id);

        return new CardSetDto(cardSet.getId(), cardSet.getName());
    }

    // TODO: Resolve N+1 problem
    @Override
    public PageDto<FlashcardDto> getFlashcardsInSet(Long id, int pageNo, int pageSize) {
        CardSet cardSet = cardSetRepository.getReferenceById(id);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Flashcard> flashcards = flashcardRepository.findAllByCardSet(cardSet, pageable);
        List<Flashcard> listOfFlashcards = flashcards.getContent();
        List<FlashcardDto> listOfDto = listOfFlashcards.stream().map(f -> Mapper.flashcardToDto(f, id)).toList();

        return new PageDto<>(
                listOfDto,
                pageNo,
                pageSize,
                flashcards.getTotalElements(),
                flashcards.getTotalPages(),
                flashcards.isLast()
        );
    }

    @Override
    public CardSetDto updateCardSet(Long id, CardSetDto dto) {
        CardSet existing = getById(id);
        CardSet incomplete = CardSet.builder()
                .name(dto.name())
                .build();
        try {
            Patcher.patchUpdate(existing, incomplete);
            cardSetRepository.save(existing);
        } catch (IllegalAccessException ex) {
            log.error(ex.getMessage());
        }

        return new CardSetDto(existing.getId(), existing.getName());
    }

    @Override
    public void deleteCardSet(Long id) {
        cardSetRepository.delete(getById(id));
    }

    private CardSet getById(Long id) {
        return cardSetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Card set with id " + id + " not found!"));
    }
}

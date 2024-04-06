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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.tsayvyac.flashcard.util.Constant.Str.NOT_FOUND;

@Slf4j(topic = "CARD_SET_SERVICE")
@Service
@RequiredArgsConstructor
class StdCardSetService implements CardSetService {
    private final CardSetRepository cardSetRepository;
    private final FlashcardRepository flashcardRepository;

    @Override
    public CardSetDto createCardSet(CardSetDto dto) {
        CardSet cardSet = cardSetRepository.save(Mapper.dtoToCardSet(dto));
        log.info("Card set with id {} was saved successfully!", cardSet.getId());

        return new CardSetDto(cardSet.getId(), cardSet.getName());
    }

    @Override
    public PageDto<CardSetDto> getCardSets(int pageNo, int pageSize) {
        Page<CardSet> cardSets = cardSetRepository.findAll(PageRequest.of(pageNo, pageSize));
        List<CardSetDto> listOfDto = cardSets.getContent().stream().map(Mapper::cardSetToDto).toList();

        return Mapper.mapToPageDto(listOfDto, pageNo, pageSize, cardSets);
    }

    @Override
    public CardSetDto getCardSetById(Long id) {
        CardSet cardSet = getById(id);

        return Mapper.cardSetToDto(cardSet);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public PageDto<FlashcardDto> getFlashcardsInSet(Long id, int pageNo, int pageSize) {
        CardSet cardSet = cardSetRepository.getReferenceById(id);
        Page<Flashcard> flashcards = flashcardRepository.findAllByCardSet(cardSet, PageRequest.of(pageNo, pageSize));
        List<FlashcardDto> listOfDto = flashcards.getContent().stream().map(f -> Mapper.flashcardToDto(f, id)).toList();

        return Mapper.mapToPageDto(listOfDto, pageNo, pageSize, flashcards);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<FlashcardDto> getRepetitionFlashcards(Long id) {
        CardSet cardSet = cardSetRepository.getReferenceById(id);
        List<Flashcard> flashcards = flashcardRepository.findFlashcardsForRepetition(cardSet);

        return flashcards.stream().map(f -> Mapper.flashcardToDto(f, id)).toList();
    }

    @Override
    @Transactional
    public CardSetDto updateCardSet(Long id, CardSetDto dto) {
        CardSet existing = getById(id);
        CardSet incomplete = Mapper.dtoToCardSet(dto);
        try {
            Patcher.patchUpdate(existing, incomplete);
            cardSetRepository.save(existing);
        } catch (IllegalAccessException ex) {
            log.error(ex.getMessage());
        }
        log.info("Card set with id {} was updated!", id);

        return Mapper.cardSetToDto(existing);
    }

    @Override
    public void deleteCardSet(Long id) {
        cardSetRepository.delete(getById(id));
        log.info("Card set with id {} was deleted!", id);
    }

    private CardSet getById(Long id) {
        return cardSetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Card set with id " + id + NOT_FOUND));
    }
}

package com.tsayvyac.flashcard.service.impl;

import com.tsayvyac.flashcard.dto.CardSetDto;
import com.tsayvyac.flashcard.dto.FlashcardDto;
import com.tsayvyac.flashcard.dto.PageDto;
import com.tsayvyac.flashcard.dto.SetsInfoDto;
import com.tsayvyac.flashcard.exception.NotFoundException;
import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;
import com.tsayvyac.flashcard.repository.CardSetRepository;
import com.tsayvyac.flashcard.repository.FlashcardRepository;
import com.tsayvyac.flashcard.service.CardSetService;
import com.tsayvyac.flashcard.util.Mapper;
import com.tsayvyac.flashcard.util.Pair;
import com.tsayvyac.flashcard.util.Patcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

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

        return new CardSetDto(cardSet.getId(), cardSet.getName(), 0, 0);
    }

    @Override
    public PageDto<CardSetDto> getCardSets(int pageNo, int pageSize, boolean isOnlyRep) {
        Page<CardSet> cardSets = cardSetRepository.findAll(PageRequest.of(pageNo, pageSize));
        Predicate<CardSetDto> predicate = isOnlyRep
                ? i -> i.countRep() > 0
                : i -> true;
        return convertPageToPageDto(
                cardSets,
                predicate,
                pageNo,
                pageSize
        );
    }

    @Override
    public List<SetsInfoDto> getSetsInfo() {
        List<CardSet> cardSetList = cardSetRepository.findAll();
        return cardSetList.stream()
                .map(Mapper::cardSetToSetsInfo)
                .toList();
    }

    @Override
    public CardSetDto getCardSetById(Long id) {
        CardSet cardSet = getById(id);
        Pair<Integer, Integer> pair = getCounts(cardSet);

        return Mapper.cardSetToDto(cardSet, pair.getFirst(), pair.getSecond());
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public PageDto<FlashcardDto> getFlashcardsInSet(Long id, int pageNo, int pageSize) {
        CardSet cardSet = cardSetRepository.getReferenceById(id);
        Page<Flashcard> flashcards = flashcardRepository.findAllPagesByCardSet(cardSet, PageRequest.of(pageNo, pageSize));
        List<FlashcardDto> listOfDto = flashcards.getContent().stream().map(f -> Mapper.flashcardToDto(f, id)).toList();

        return Mapper.mapToPageDto(listOfDto, pageNo, pageSize, flashcards);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<FlashcardDto> getFlashcardsFromSet(Long id, boolean isCram) {
        CardSet cardSet = cardSetRepository.getReferenceById(id);
        List<Flashcard> flashcards = isCram
                ? flashcardRepository.findAllByCardSet(cardSet)
                : flashcardRepository.findFlashcardsForRepetition(cardSet);

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

        Pair<Integer, Integer> pair = getCounts(existing);
        return Mapper.cardSetToDto(existing, pair.getFirst(), pair.getSecond());
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

    private Pair<Integer, Integer> getCounts(CardSet cardSet) {
        Integer countRep = flashcardRepository.countFlashcardsForRepetition(cardSet);
        Integer countAll = flashcardRepository.countFlashcardByCardSet(cardSet);
        return Pair.of(countRep, countAll);
    }

    private PageDto<CardSetDto> convertPageToPageDto(
            Page<CardSet> cardSets,
            Predicate<CardSetDto> predicate,
            int pageNo,
            int pageSize
    ) {
        List<CardSetDto> listOfDto = cardSets.getContent().stream()
                .map(cardSet -> {
                            Pair<Integer, Integer> pair = getCounts(cardSet);
                            return Mapper.cardSetToDto(cardSet, pair.getFirst(), pair.getSecond());
                        }
                )
                .filter(predicate)
                .sorted(Comparator.comparing(CardSetDto::countRep).reversed())
                .toList();

        return Mapper.mapToPageDto(listOfDto, pageNo, pageSize, cardSets);
    }
}

package com.tsayvyac.flashcard.service.impl;

import com.tsayvyac.flashcard.dto.CardSetModelDto;
import com.tsayvyac.flashcard.dto.CardSetRequestDto;
import com.tsayvyac.flashcard.exception.CardSetNotFound;
import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.repository.CardSetRepository;
import com.tsayvyac.flashcard.service.CardSetService;
import com.tsayvyac.flashcard.util.Mapper;
import com.tsayvyac.flashcard.util.Patcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class StdCardSetService implements CardSetService {
    private final CardSetRepository repository;

    @Override
    public CardSetModelDto createCardSet(CardSetModelDto dto) {
        CardSet cardSet = repository.save(CardSet.builder()
                .name(dto.name())
                .build()
        );

        return Mapper.cardSetToDto(cardSet);
    }

    @Override
    public CardSetModelDto getCardSet(Long id) {
        return Mapper.cardSetToDto(getCardSetById(id));
    }

    @Override
    public CardSetModelDto updateCardSet(Long id, CardSetRequestDto dto) {
        CardSet incomplete = Mapper.requestDtoToCardSet(dto);

        return Mapper.cardSetToDto(update(id, incomplete));
    }

    protected CardSet update(Long id, CardSet incomplete) {
        CardSet existing = getCardSetById(id);

        try {
            Patcher.patchUpdate(existing, incomplete);
            repository.save(existing);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return existing;
    }

    CardSet getCardSetById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CardSetNotFound("Card set with id " + id + " not found!"));
    }
}

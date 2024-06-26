package com.tsayvyac.flashcard.repository;

import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    @Query("select f from Flashcard f left join fetch f.progress where f.cardSet = :cardSet")
    Page<Flashcard> findAllPagesByCardSet(CardSet cardSet, Pageable pageable);

    @Query("select f from Flashcard f left join fetch f.progress p where f.cardSet = :cardSet and p.nextDate <= current_date")
    List<Flashcard> findFlashcardsForRepetition(CardSet cardSet);

    List<Flashcard> findAllByCardSet(CardSet cardSet);

    @Query("select count(f) from Flashcard f left join f.progress p where f.cardSet = :cardSet and p.nextDate <= current_date")
    Integer countFlashcardsForRepetition(CardSet cardSet);

    Integer countFlashcardByCardSet(CardSet cardSet);
}

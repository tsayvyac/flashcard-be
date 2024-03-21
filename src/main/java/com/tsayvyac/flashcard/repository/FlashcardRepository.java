package com.tsayvyac.flashcard.repository;

import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Flashcard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    @Query("select count(f.id) from Flashcard f where f.cardSet = :cardSet")
    Integer getCountOfFlashcardsByCardSetId(CardSet cardSet);

    @Query("select f from Flashcard f left join fetch f.progress where f.cardSet = :cardSet")
    Page<Flashcard> findAllByCardSet(CardSet cardSet, Pageable pageable);
}

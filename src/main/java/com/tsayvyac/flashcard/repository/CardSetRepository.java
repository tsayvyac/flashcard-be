package com.tsayvyac.flashcard.repository;

import com.tsayvyac.flashcard.model.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardSetRepository extends JpaRepository<CardSet, Long> {
}

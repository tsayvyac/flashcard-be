package com.tsayvyac.flashcard.repository;

import com.tsayvyac.flashcard.model.CardSet;
import com.tsayvyac.flashcard.model.Learner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardSetRepository extends JpaRepository<CardSet, Long> {

    Page<CardSet> findAllByLearner(Pageable pageable, Learner learner);
    List<CardSet> findAllByLearner(Learner learner);
    Optional<CardSet> findByIdAndLearner(Long id, Learner learner);
}

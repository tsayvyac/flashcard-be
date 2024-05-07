package com.tsayvyac.flashcard.repository;

import com.tsayvyac.flashcard.model.Learner;
import com.tsayvyac.flashcard.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    @Query("select count(p) from Progress p where p.lastScore = 1 and p.learner = :learner")
    Integer countOfGoodScore(Learner learner);
}

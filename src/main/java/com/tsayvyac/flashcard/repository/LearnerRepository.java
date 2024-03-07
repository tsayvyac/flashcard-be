package com.tsayvyac.flashcard.repository;

import com.tsayvyac.flashcard.model.Learner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnerRepository extends CrudRepository<Learner, Long> {
}

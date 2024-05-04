package com.tsayvyac.flashcard.repository;

import com.tsayvyac.flashcard.model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearnerRepository extends JpaRepository<Learner, Long> {
    Optional<Learner> findByUsername(String username);
    boolean existsByUsernameAndEmail(String username, String email);
}

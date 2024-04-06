package com.tsayvyac.flashcard.service.impl;

import com.tsayvyac.flashcard.config.AsyncConfig;
import com.tsayvyac.flashcard.exception.NotFoundException;
import com.tsayvyac.flashcard.model.Progress;
import com.tsayvyac.flashcard.repository.ProgressRepository;
import com.tsayvyac.flashcard.service.impl.strategy.ContextStrategy;
import com.tsayvyac.flashcard.util.Patcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.tsayvyac.flashcard.util.Constant.Str.NOT_FOUND;

@Slf4j(topic = "ASYNC_SERVICE")
@RequiredArgsConstructor
@Service
class AsyncService {
    private final ProgressRepository progressRepository;

    @Async(AsyncConfig.TASK_EXEC_REPETITION)
    public void updateProgress(Long id, int score) {
        log.info("Executed asynchronously by {}", Thread.currentThread().getName());

        ContextStrategy ctx = new ContextStrategy();
        Progress existing = getProgressById(id);
        Progress incomplete = ctx.computeDate(existing, score);
        try {
            Patcher.patchUpdate(existing, incomplete);
            progressRepository.save(existing);
        } catch (IllegalAccessException ex) {
            log.error(ex.getMessage());
        }

        log.info("Progress {} was successfully updated", existing);
    }

    private Progress getProgressById(Long id) {
        return progressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Progress for flashcard with id " + id + NOT_FOUND));
    }
}

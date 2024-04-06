package com.tsayvyac.flashcard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {
    private final ApplicationProperties applicationProperties;
    private static final String TASK_EXEC_DEFAULT_PREFIX = "taskExecutor-";
    private static final String TASK_EXEC_REPETITION_PREFIX = "repetitionTaskExecutor-";
    public static final String TASK_EXEC_DEFAULT = "taskExecutor";
    public static final String TASK_EXEC_REPETITION = "repetitionTaskExecutor";

    @Override
    @Bean(name = TASK_EXEC_DEFAULT)
    public Executor getAsyncExecutor() {
        return taskExecutor(TASK_EXEC_DEFAULT_PREFIX);
    }

    @Bean(name = TASK_EXEC_REPETITION)
    public Executor getRepetitionExecutor() {
        return taskExecutor(TASK_EXEC_REPETITION_PREFIX);
    }

    private Executor taskExecutor(final String namePrefix) {
        final ApplicationProperties.Async async = applicationProperties.getAsync();
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(async.getCorePoolSize());
        executor.setMaxPoolSize(async.getMaxPoolSize());
        executor.setQueueCapacity(async.getQueueCapacity());
        executor.setThreadNamePrefix(namePrefix);
        executor.initialize();
        return executor;
    }
}

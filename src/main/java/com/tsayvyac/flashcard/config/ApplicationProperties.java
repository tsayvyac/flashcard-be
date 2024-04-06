package com.tsayvyac.flashcard.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = ApplicationProperties.CONF_PROP_PREFIX, ignoreUnknownFields = false)
@Getter
public class ApplicationProperties {
    static final String CONF_PROP_PREFIX = "app";
    private final Async async = new Async();

    @Getter
    @Setter
    public static class Async {
        private Integer corePoolSize;
        private Integer maxPoolSize;
        private Integer queueCapacity;
    }
}

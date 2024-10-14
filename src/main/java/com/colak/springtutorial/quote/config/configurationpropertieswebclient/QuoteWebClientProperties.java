package com.colak.springtutorial.quote.config.configurationpropertieswebclient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@ConfigurationProperties("webclient.quote")
@Component
@Getter
@Setter
@ToString
public class QuoteWebClientProperties {

    private Generic generic;

    private Specific specific;

    @Getter
    @Setter
    @ToString
    public static class Generic {
        private Connection connection;
    }

    @Getter
    @Setter
    @ToString
    public static class Specific {
        private String name;
        private Connection connection;
        private Retry retry;
        private Timeout timeout;
        private boolean enabledMetrics;
    }

    @Getter
    @Setter
    @ToString
    public static class Retry {
        private List<Integer> retryableCodes;
        private Long retryAttempts;
        private Long retryDelay;
    }

    @Getter
    @Setter
    @ToString
    public static class Connection {
        private String providerName;
        private Integer max;
    }

    @Getter
    @Setter
    @ToString
    public static class Timeout {
        private Duration responseTimeout;
        private Duration keepAlive;
        private Duration evictionInterval;
    }
}

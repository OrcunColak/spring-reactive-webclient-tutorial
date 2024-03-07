package com.colak.springreactivewebclienttutorial.quote.config.sslpinningwebclient;

import lombok.Getter;

import java.time.Duration;

@Getter()
public class ExternalServiceProperties {
    private String url;

    private String certificateFingerprint;

    int maxConnections;

    private Duration pendingAcquireTimeout;

    private Duration maxIdleTime;

    private Duration maxLifeTime;

    private Duration evictInBackground;
}

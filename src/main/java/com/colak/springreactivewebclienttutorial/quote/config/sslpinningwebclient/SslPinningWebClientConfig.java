package com.colak.springreactivewebclienttutorial.quote.config.sslpinningwebclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class SslPinningWebClientConfig {

    @Bean
    SecureWebClientFactory secureWebClientFactory(
            ExternalServiceProperties externalServiceProperties,
            ConnectionProvider connectionProvider) {
        return new SecureWebClientFactory(
                externalServiceProperties.getUrl(),
                externalServiceProperties.getCertificateFingerprint(),
                connectionProvider
        );
    }

    @Bean
    ConnectionProvider connectionProvider(ExternalServiceProperties externalServiceProperties) {
        return ConnectionProvider.builder("customConnectionProvider")
                .maxConnections(externalServiceProperties.getMaxConnections())
                .pendingAcquireTimeout(externalServiceProperties.getPendingAcquireTimeout())
                .maxIdleTime(externalServiceProperties.getMaxIdleTime())
                .maxLifeTime(externalServiceProperties.getMaxLifeTime())
                .evictInBackground(externalServiceProperties.getEvictInBackground())
                .build();
    }

}

package com.colak.springreactivewebclienttutorial.quote.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

/**
 * The default HttpClient used by WebClient is Netty.
 * There is no straight way to log request and response including body in a specific format using Netty.
 * So we are going to change WebClient default http client to jetty.
 * <p>
 * Jetty uses listeners to provide hooks for all possible request and response events.
 * We will use these hooks to log request and response content.
 */
@Configuration
@Slf4j
public class LoggingWebClientConfig {

    @Bean(name = "loggingWebclient")
    protected WebClient loggingWebclient(LoggerService loggerService) {
        //We need to create a new Jetty Http Client, and we should override new request method.
        HttpClient httpClient = new HttpClient() {
            @Override
            public Request newRequest(URI uri) {
                Request request = super.newRequest(uri);
                // We need to pass request to loggerservice for logging.
                return loggerService.logRequestResponse(request);
            }
        };
        return WebClient.builder()
                .clientConnector(new JettyClientHttpConnector(httpClient))
                .build();
    }
}

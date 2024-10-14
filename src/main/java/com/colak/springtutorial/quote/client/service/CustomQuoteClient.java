package com.colak.springtutorial.quote.client.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomQuoteClient {

    private final WebClient quoteWebclient;

    public CustomQuoteClient(@Qualifier("quoteWebclient") WebClient quoteWebclient) {
        this.quoteWebclient = quoteWebclient;
    }

    public List<String> getQuotes() {
        Flux<String> flux = getQuotesByWebFlux(quoteWebclient);
        return flux.collectList().block();
    }

    public Flux<String> getQuotesByWebFlux(WebClient webClient) {
        List<Flux<String>> list = new ArrayList<>();

        for (int index = 0; index < 3; index++) {
            Flux<String> flux = webClient
                    .get()
                    .uri("http://localhost:8080/api/v1/quote/getQuotes")
                    .retrieve()
                    .bodyToFlux(String.class);
            list.add(flux);
        }
        return Flux.merge(list);
    }
}

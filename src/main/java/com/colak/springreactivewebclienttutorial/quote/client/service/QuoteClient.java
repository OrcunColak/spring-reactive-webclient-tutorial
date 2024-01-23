package com.colak.springreactivewebclienttutorial.quote.client.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteClient {

    public Flux<String> getQuotesByWebFlux() {

        WebClient webClient = WebClient.create("http://localhost:8080/api/v1/quote");

        List<Flux<String>> list = new ArrayList<>();

        for (int index = 0; index < 3; index++) {
            Flux<String> flux = webClient
                    .get()
                    .uri("/getQuotes")
                    .retrieve()
                    .bodyToFlux(String.class);
            list.add(flux);
        }
        return Flux.merge(list);
    }

    public List<String> getQuotes() {
        Flux<String> flux = getQuotesByWebFlux();
        return flux.collectList().block();
    }
}

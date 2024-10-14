package com.colak.springtutorial.quote.external.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quote")
public class QuoteController {

    // http://localhost:8080/api/v1/quote/getQuote
    @GetMapping(path = "getQuote")
    public Mono<String> getQuote() {
        return Mono.just("quote1");
    }

    // http://localhost:8080/api/v1/quote/getQuotes
    @GetMapping(path = "getQuotes")
    public Flux<String> getQuotes() {
        List<String> list = List.of("quote1", "quote2");
        return Flux.fromIterable(list);
    }
}

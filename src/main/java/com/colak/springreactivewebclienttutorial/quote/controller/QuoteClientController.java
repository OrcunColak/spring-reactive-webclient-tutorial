package com.colak.springreactivewebclienttutorial.quote.controller;

import com.colak.springreactivewebclienttutorial.quote.client.service.QuoteClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/quoteclient")
@RequiredArgsConstructor
public class QuoteClientController {

    private final QuoteClient quoteClient;

    @GetMapping(path = "getQuotesByWebFlux")
    public Flux<String> getQuotesByWebFlux() {
        return quoteClient.getQuotesByWebFlux();
    }

}

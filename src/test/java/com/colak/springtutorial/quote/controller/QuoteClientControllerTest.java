package com.colak.springtutorial.quote.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class QuoteClientControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetQuotes() {
        webTestClient.get()
                .uri("/api/v1/quoteclient/getQuotesByWebFlux")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(String.class)
                .isEqualTo(List.of("quote1quote2quote1quote2quote1quote2"));


    }
}

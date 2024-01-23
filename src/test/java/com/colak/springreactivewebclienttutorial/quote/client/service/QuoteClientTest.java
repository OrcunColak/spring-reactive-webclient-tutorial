package com.colak.springreactivewebclienttutorial.quote.client.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class QuoteClientTest {


    @Autowired
    private QuoteClient quoteClient;

    @Test
    void testGetQuotes() {
        List<String> result = quoteClient.getQuotes();
        List<String> expected = List.of("quote1quote2", "quote1quote2", "quote1quote2");
        Assertions.assertEquals(expected, result);
    }
}

package com.colak.springtutorial.quote.client.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LoggingQuoteClientTest {

    @Autowired
    private LoggingQuoteClient quoteClient;

    @Test
    void testGetQuotes() {
        List<String> result = quoteClient.getQuotesByLogging();
        List<String> expected = List.of("quote1quote2", "quote1quote2", "quote1quote2");
        Assertions.assertEquals(expected, result);
    }
}

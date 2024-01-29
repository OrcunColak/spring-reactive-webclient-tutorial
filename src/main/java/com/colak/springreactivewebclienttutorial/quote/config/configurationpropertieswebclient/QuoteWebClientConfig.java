package com.colak.springreactivewebclienttutorial.quote.config.configurationpropertieswebclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.nio.charset.StandardCharsets;

@Configuration
@ConditionalOnProperty(prefix = "webclient.quote.specific.connection", name = "max")
@RequiredArgsConstructor
@Slf4j
public class QuoteWebClientConfig implements WebFluxConfigurer {

    private final QuoteWebClientProperties quoteWebClientProperties;

    @Bean(name = "quoteWebclient")
    public WebClient quoteWebclient() {
        log.info("Quote Web Client Initialization-Begin-{}", quoteWebClientProperties);

        QuoteWebClientProperties.Specific specific = quoteWebClientProperties.getSpecific();
        QuoteWebClientProperties.Connection connection = specific.getConnection();
        QuoteWebClientProperties.Timeout timeout = specific.getTimeout();

        ClientHttpConnector connector = getClientHttpConnector(connection, specific, timeout);

        WebClient webClient = WebClient
                .builder()
                .clientConnector(connector)
                .codecs(clientCodecConfigurer ->
                        // support for XML
                {
                    Jaxb2XmlDecoder jaxb2XmlDecoder = new Jaxb2XmlDecoder(
                            new MediaType(MediaType.TEXT_XML, StandardCharsets.UTF_8),
                            new MimeType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8)
                    );
                    clientCodecConfigurer.defaultCodecs()
                            .jaxb2Decoder(jaxb2XmlDecoder);
                })
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        log.info("Quote Web Client Initialization-End");
        return webClient;
    }

    /**
     * In Spring WebFlux, WebClient uses a client connector to establish and manage HTTP connections.
     * The client connector is responsible for handling low-level details related to connecting to a server,
     * managing connections, and processing HTTP requests and responses.
     * <p>
     * By default, WebClient uses the Reactor Netty library as the client connector, but you can customize it based on
     * your requirements.
     */
    private ClientHttpConnector getClientHttpConnector(QuoteWebClientProperties.Connection connection,
                                                       QuoteWebClientProperties.Specific specific,
                                                       QuoteWebClientProperties.Timeout timeout) {
        ConnectionProvider connectionProvider = ConnectionProvider
                .builder("quote-webclient-connectionProvider")
                .maxConnections(connection.getMax())
                .name(specific.getName())
                // Metrics for Web client
                .metrics(specific.isEnabledMetrics())
                .build();

        // HttpClient is from reactor netty project
        HttpClient httpClient = HttpClient
                .create(connectionProvider)
                // Setting Response Timeout
                .responseTimeout(timeout.getResponseTimeout());

        return new ReactorClientHttpConnector(httpClient);
    }
}

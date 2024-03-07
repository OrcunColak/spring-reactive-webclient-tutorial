package com.colak.springreactivewebclienttutorial.quote.config.sslpinningwebclient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SimpleTrustManagerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import java.security.KeyStore;

public class SecureWebClientFactory {
    private final String pinnedCertificateSha256Value;
    private final String baseUrl;
    private final ConnectionProvider connectionProvider;

    public SecureWebClientFactory(String baseUrl,
                                  String pinnedCertificateSha256Value,
                                  ConnectionProvider connectionProvider) {
        this.baseUrl = baseUrl;
        this.pinnedCertificateSha256Value = pinnedCertificateSha256Value;
        this.connectionProvider = connectionProvider;
    }

    public WebClient createWebClient() throws Exception {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(createHttpClient()))
                .build();
    }

    private HttpClient createHttpClient() throws Exception {
        SslContext sslContext = createNettySslContext();
        return HttpClient.create(connectionProvider).secure(sslSpec -> sslSpec.sslContext(sslContext));
    }

    private SslContext createNettySslContext() throws Exception {
        TrustManager[] trustManagers = {new PinningTrustManager(this.pinnedCertificateSha256Value)};
        return SslContextBuilder.forClient()
                .trustManager(new SimpleTrustManagerFactory() {
                    @Override
                    protected void engineInit(KeyStore keyStore) {
                    }

                    @Override
                    protected void engineInit(ManagerFactoryParameters managerFactoryParameters) {
                    }

                    @Override
                    protected TrustManager[] engineGetTrustManagers() {
                        return trustManagers;
                    }
                })
                .build();
    }
}
# # WebClient.get

Flux<String> flux = webclient.get().uri("...").retrieve().bodyToFlux(String.class);

# Customization

Most of the code is as below.   
We need to create a custom **reactor.netty.http.client.HttpClient** and pass to as clientConnector()
**reactor.netty.http.client.HttpClient** class can have a custom  **reactor.netty.resources.ConnectionProvider**. Such as timeout etc.
**reactor.netty.http.client.HttpClient** class can have a custom  **SsllContext**

```
 WebClient createWebClient() throws Exception {
  return WebClient.builder()
    .baseUrl(baseUrl)
    .clientConnector(new ReactorClientHttpConnector(createHttpClient()))
    .build();
}
```

# Codecs

WebClient can have codecs
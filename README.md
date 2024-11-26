# Read Me First

The original idea is from  
https://engineering.hexacta.com/quarkus-restclient-vs-spring-webflux-performance-comparison-for-consume-asynchronous-rest-apis-bfe60f61f579

# Custom WebClient

The original idea is from  
https://medium.com/@dixitsatish34/custom-webclient-configuration-in-spring-boot-e1f9a1e45a47

# Streaming

```
import org.springframework.web.reactive.function.client.WebClient;

WebClient client = WebClient.create();
client.get()
      .uri("http://example.com/large-json")
      .retrieve()
      .bodyToFlux(String.class) // Stream the response
      .subscribe(data -> System.out.println("Received chunk: " + data));
```




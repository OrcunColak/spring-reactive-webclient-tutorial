# # WebClient.get

Flux<String> flux = webclient.get().uri("...").retrieve().bodyToFlux(String.class);

# WebTestClient.post

ApiErrorResponseDto errorResponse = WebTestClient
.post().uri("...").contentType(MediaType.APPLICATION_JSON).bodyValue(request).exchange()
.expectStatus().isCreated()
.expectBody(ApiErrorResponseDto.class).returnResult().getResponseBody();


# # WebClient.get

Flux<String> flux = webclient.get().uri("...").retrieve().bodyToFlux(String.class);

# WebTestClient.get single

webTestClient.get().uri("...").exchange().expectStatus().isOk().expectList(Student.class)
.isEqualTo(...);

# WebTestClient.get list

webTestClient.get().uri("...").exchange().expectStatus().isOk().expectBodyList(Student.class);
.isEqualTo(List.of();

# WebTestClient.post Object

Student student = ...;

webTestClient.post().uri("...").body(Mono.just(student), Student.class).exchange()
.expectStatus().isCreated();

webTestClient.put().uri("...").body(Mono.just(updatedStudent), Student.class).exchange()
.expectStatus().isOk()
.expectBody(String.class).isEqualTo("Student updated successfully");

# WebTestClient.post Json String

String json =
ApiErrorResponseDto errorResponse = WebTestClient
.post().uri("...").contentType(MediaType.APPLICATION_JSON).bodyValue(json).exchange()
.expectStatus().isCreated()
.expectBody(ApiErrorResponseDto.class).returnResult().getResponseBody();


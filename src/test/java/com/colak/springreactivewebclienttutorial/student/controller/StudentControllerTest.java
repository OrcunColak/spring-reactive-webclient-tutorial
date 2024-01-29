package com.colak.springreactivewebclienttutorial.student.controller;

import com.colak.springreactivewebclienttutorial.student.dto.StudentDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentDtoControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void testEnrollStudent() {
        StudentDto StudentDto = new StudentDto(null, "John Doe");

        webTestClient.post()
                .uri("/api/v1/student/enroll")
                .body(Mono.just(StudentDto), StudentDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class).isEqualTo("Student enrolled successfully. Student ID: 1");
    }

    @Test
    @Order(2)
    void testUpdateStudent() {
        StudentDto StudentDto = new StudentDto(null, "Jane Doe");

        webTestClient.post()
                .uri("/api/v1/student/enroll")
                .body(Mono.just(StudentDto), StudentDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class).isEqualTo("Student enrolled successfully. Student ID: 2");

        StudentDto updatedStudentDto = new StudentDto(2L, "Updated Name");
        webTestClient.put()
                .uri("/api/v1/student/update/" + updatedStudentDto.getId())
                .body(Mono.just(updatedStudentDto), StudentDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Student with ID 2 updated successfully");
    }

    @Test
    @Order(3)
    void testGetAllStudents() {
        List<StudentDto> expected = List.of(
                new StudentDto(1L, "John Doe"),
                new StudentDto(2L, "Updated Name")
        );
        webTestClient.get()
                .uri("/api/v1/student/list")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StudentDto.class)
                .isEqualTo(expected);
    }
}

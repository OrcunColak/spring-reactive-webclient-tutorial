package com.colak.springreactivewebclienttutorial.student.controller;

import com.colak.springreactivewebclienttutorial.student.dto.StudentDto;
import com.colak.springreactivewebclienttutorial.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/enroll")
    public Mono<ResponseEntity<String>> enrollStudent(@RequestBody StudentDto student) {
        return studentService.enrollStudent(student)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<String>> updateStudent(@PathVariable Long id, @RequestBody StudentDto updatedStudent) {
        return studentService.updateStudent(id, updatedStudent)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found"));
    }

    @GetMapping("/list")
    public Flux<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }
}

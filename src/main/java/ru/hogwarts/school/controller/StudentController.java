package ru.hogwarts.school.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/student")
@Tag(name = "API для работы с студентами")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    @Operation(summary = "поиск студента")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("age")
    @Operation(summary = "поиск студента по возрасту")
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping
    @Operation(summary = "поиск студентов в промежутке возраста")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam int min,
                                                                @RequestParam int max) {
        return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
    }

    @GetMapping("/")
    @Operation(summary = "все студенты")
    public ResponseEntity<Collection<Student>> GetAllStudents() {
        Collection<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PostMapping
    @Operation(summary = "добавить студента")
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    @Operation(summary = "изменить студента")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "удалить студента")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/faculty")
    @Operation(summary = "поиск факультета студента")
    public ResponseEntity<Faculty> findByFacultyStudent(Long id) {
        return ResponseEntity.ok(studentService.findByFacultyStudent(id));
    }

    @GetMapping("count")
    @Operation(summary = "Получение колличества студентов")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(studentService.getCount());
    }

    @GetMapping("age/average")
    @Operation(summary = "Получение среднего возраста студентов")
    public ResponseEntity<Float> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("last-five")
    @Operation(summary = "Получение последних 5 студентов")
    public ResponseEntity<Collection<Student>> getLastFive() {
        return ResponseEntity.ok(studentService.getLastFive());
    }

    @GetMapping("/getNameIsStartsA")
    @Operation(summary = "Получение имён студентов на букву А")
    public ResponseEntity<Collection<String>> getStudentsNameIsStartsFromA() {
        return ResponseEntity.ok(studentService.getStudentsNameIsStartsFromA());
    }

    @GetMapping("/getAverageAge")
    @Operation(summary = "Получение среднего возраста студентов")
    public ResponseEntity<Double> getAverageAgeOfAllStudentsWithStreams() {
        return ResponseEntity.ok(studentService.getAverageAgeOfAllStudentsWithStreams());
    }

    @GetMapping("/printInConsoleListOfStudentsNamesWithThreads")
    @Operation(summary = "Печать студентов  в консоль")
    public ResponseEntity<String> printInConsoleListOfStudentsNamesWithThreads() {
        studentService.printInConsoleListOfStudentsNamesWithThreads();
        return ResponseEntity.ok("Результат в консоли");
    }

    @GetMapping("/printInConsoleListOfStudentsNamesWithSynchronizedThreads")
    @Operation(summary = "Печать студентов  в консоль")
    public ResponseEntity<String> printInConsoleListOfStudentsNamesWithSynchronizedThreads() {
        studentService.printInConsoleListOfStudentsNamesWithSynchronizedThreads();
        return ResponseEntity.ok("Результат в консоли");
    }

}

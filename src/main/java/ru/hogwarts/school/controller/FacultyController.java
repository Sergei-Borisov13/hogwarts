package ru.hogwarts.school.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;


@RestController
@RequestMapping("/faculty")
@Tag(name = "API для работы с факультетами")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    @Operation(summary = "поиск факультета")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    @Operation(summary = "добавление факультета")
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    @Operation(summary = "изменение факультета")
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "удаление факультета")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
            facultyService.deleteFaculty(id);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    @Operation(summary = "все факультеты")
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        Collection<Faculty> faculties = facultyService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping ("find")
    @Operation(summary = "поиск факультета по имени или цвету")
    public ResponseEntity<Collection<Faculty>> getColorOrName(@RequestParam String nameOrColor) {
        return ResponseEntity.ok(facultyService.findByNameOrColor(nameOrColor));
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "поиск студентов факультета")
    public ResponseEntity<Collection<Student>> findByStudentsFaculty (@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.findByStudentsFaculty(id));
    }

    @GetMapping("/getLongestFacultyName")
    @Operation(summary = "поиск факультета с самым длинным названием")
    public ResponseEntity<String> getLongestFacultyName() {
        return ResponseEntity.ok(facultyService.getLongestFacultyName());
    }
}

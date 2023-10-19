package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;


@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for add faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method for find faculty");
        return facultyRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty");
        Faculty faculty = findFaculty(id);
        facultyRepository.delete(faculty);
        return faculty;
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByNameOrColor(String nameOrColor) {
        logger.info("Was invoked method for get faculty by name or color");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    public Collection<Student> findByStudentsFaculty(Long id) {
        logger.info("Was invoked method for get students faculty");
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .orElse(null);
    }

    public String getLongestFacultyName() {
        logger.info("Was invoked method get longest faculty name");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();
    }

}

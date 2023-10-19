package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method for add student");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method for find student");
        return studentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        logger.info("Was invoked method for delete student");
        Student student = findStudent(id);
        studentRepository.delete(student);
        return student;
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for get student by age");
        return studentRepository.findByAge(age);
    }
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all student");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for student be age between");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty findByFacultyStudent(Long id) {
        logger.info("Was invoked method for get faculty student");
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .orElse(null);
    }

    public Integer getCount() {
        logger.info("Was invoked method for get count student");
        return studentRepository.getCount();
    }

    public Float getAverageAge() {
        logger.info("Was invoked method for get student average age");
        return studentRepository.getAverageAge();
    }

    public Collection<Student> getLastFive() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastFive();
    }

    public Collection<String> getStudentsNameIsStartsFromA() {
        logger.info("Was invoked method get students name is starts from A");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(names -> names.startsWith("А"))
                .sorted()
                .toList();
    }
    public Double getAverageAgeOfAllStudentsWithStreams() {
        logger.info("Was invoked method get average age of all students with streams");
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(Double.NaN);
    }

}

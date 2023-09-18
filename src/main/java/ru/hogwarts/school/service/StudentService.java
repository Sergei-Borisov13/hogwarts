package ru.hogwarts.school.service;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            return student.get();
        } else {
            throw new NotFoundException();
        }
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        Student student = findStudent(id);
        studentRepository.deleteById(id);
        return student;
    }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }
    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty findByFacultyStudent(Long id) {
        return studentRepository.findById(id).get().getFaculty();
    }

}

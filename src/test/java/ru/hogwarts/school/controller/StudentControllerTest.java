package ru.hogwarts.school.controller;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;


import static org.assertj.core.api.Assertions.assertThat;
import static ru.hogwarts.school.controller.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;

    @Test
    public void createStudent() {
        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);

        ResponseEntity<Student> newStudentRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        Student newStudent = newStudentRs.getBody();

        assertThat(newStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(newStudent.getName()).isEqualTo(student.getName());
        assertThat(newStudent.getAge()).isEqualTo(student.getAge());
    }

    @Test
    public void getStudentById() {
        Student newStudent = createTestStudent();

        ResponseEntity<Student> getStudentRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class);

        Student studentRq = getStudentRs.getBody();

        assertThat(studentRq.getId()).isEqualTo(newStudent.getId());
        assertThat(studentRq.getName()).isEqualTo(newStudent.getName());
        assertThat(studentRq.getAge()).isEqualTo(newStudent.getAge());
    }

    @Test
    public void deleteStudent() {

        Student newStudent = createTestStudent();

        testRestTemplate.delete("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class);

        ResponseEntity<Student> getStudentRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class);

        Student studentRq = getStudentRs.getBody();

        assertThat(studentRq.getName()).isNull();
    }
    @Test
    public void getStudentsByAge() {
        Student newStudent = createTestStudent();

        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/student?min=9&max=15", String.class))
                .isNotNull();
    }

    @Test
    public void getAllStudents() {
        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/student/", String.class))
                .isNotNull();
    }

    @Test
    public void updateStudent() {
        Student newStudent = createTestStudent();
        newStudent.setName(MOCK_STUDENT_OTHER_NAME);
        testRestTemplate.put("http://localhost:" + port + "/student", newStudent, Student.class);

        ResponseEntity<Student> getStudentRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class);
        Student studentRq = getStudentRs.getBody();

        assertThat(studentRq.getName()).isEqualTo(newStudent.getName());

    }

    public Student createTestStudent() {
        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);

        ResponseEntity<Student> newStudentRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);

        assertThat(newStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        return newStudentRs.getBody();
    }
}
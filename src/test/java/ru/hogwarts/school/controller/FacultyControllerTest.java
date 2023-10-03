package ru.hogwarts.school.controller;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.controller.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;

    @Test
    public void createFaculty() {
        Faculty faculty = new Faculty();
        faculty.setId(MOCK_FACULTY_ID);
        faculty.setName(MOCK_FACULTY_NAME);
        faculty.setColor(MOCK_FACULTY_COLOR);

        ResponseEntity<Faculty> newFacultyRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        Faculty newFaculty = newFacultyRs.getBody();

        assertThat(newFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(newFaculty.getName()).isEqualTo(faculty.getName());
        assertThat(newFaculty.getColor()).isEqualTo(faculty.getColor());
    }

    @Test
    public void getFacultyById() {
        Faculty newFaculty = createTestFaculty();

        ResponseEntity<Faculty> getFacultyRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + newFaculty.getId(), Faculty.class);

        Faculty facultyRq  = getFacultyRs.getBody();

        assertThat(facultyRq.getId()).isEqualTo(newFaculty.getId());
        assertThat(facultyRq.getName()).isEqualTo(newFaculty.getName());
        assertThat(facultyRq.getColor()).isEqualTo(newFaculty.getColor());
    }

    @Test
    public void deleteFaculty() {
        Faculty newFaculty = createTestFaculty();

        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + newFaculty.getId(), Faculty.class);

        ResponseEntity<Faculty> getFacultyRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + newFaculty.getId(), Faculty.class);

        Faculty facultyRq = getFacultyRs.getBody();

        assertThat(facultyRq.getName()).isNull();
    }
    @Test
    public void getAllFaculties() {
        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/", String.class))
                .isNotNull();
    }

    @Test
    public void updateStudent() {
        Faculty newFaculty = createTestFaculty();
        newFaculty.setName(MOCK_FACULTY_OTHER_NAME);
        testRestTemplate.put("http://localhost:" + port + "/faculty", newFaculty, Faculty.class);

        ResponseEntity<Faculty> getFacultyRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + newFaculty.getId(), Faculty.class);
        Faculty facultyRq = getFacultyRs.getBody();

        assertThat(facultyRq.getName()).isEqualTo(newFaculty.getName());
    }

    @Test
    public void getFacultyByNameOrColor() {
        Faculty newFaculty = createTestFaculty();

        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/find?nameOrColor=Faculty name", String.class))
                .isNotNull();
    }

    public Faculty createTestFaculty() {
        Faculty faculty = new Faculty();
        faculty.setId(MOCK_FACULTY_ID);
        faculty.setName(MOCK_FACULTY_NAME);
        faculty.setColor(MOCK_FACULTY_COLOR);

        ResponseEntity<Faculty> newFacultyRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        assertThat(newFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        return newFacultyRs.getBody();
    }
}
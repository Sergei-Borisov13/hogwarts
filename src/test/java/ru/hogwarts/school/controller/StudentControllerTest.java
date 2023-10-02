package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.controller.ConstantsTest.*;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createStudent() throws Exception {
        JSONObject createStudentRq = new JSONObject();
        createStudentRq.put("name",MOCK_STUDENT_NAME);
        createStudentRq.put("age",MOCK_STUDENT_AGE);

        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);


        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(createStudentRq.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }
    @Test
    public void getStudentById() throws Exception {
        JSONObject createStudentRq = new JSONObject();
        createStudentRq.put("name",MOCK_STUDENT_NAME);
        createStudentRq.put("age",MOCK_STUDENT_AGE);

        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);


        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE))
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID));
    }
    @Test
    public void updateStudent() throws Exception {
        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        student.setName(MOCK_STUDENT_OTHER_NAME);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        JSONObject updateStudentRq = new JSONObject();
        updateStudentRq.put("id",MOCK_STUDENT_ID);
        updateStudentRq.put("name",MOCK_STUDENT_NAME);
        updateStudentRq.put("age",MOCK_STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(updateStudentRq.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID))
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_OTHER_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }
    @Test
    public void deleteStudent() throws Exception {
        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllStudents() throws Exception {
        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);

        List<Student> Students = Collections.singletonList(student);
        when(studentRepository.findAll()).thenReturn(Students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Students)));
    }


}
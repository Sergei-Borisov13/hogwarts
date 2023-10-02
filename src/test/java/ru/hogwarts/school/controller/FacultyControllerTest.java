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
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.controller.ConstantsTest.*;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private FacultyController facultyController;

    ObjectMapper mapper = new ObjectMapper();


    @Test
    public void createFaculty() throws Exception {
        JSONObject createFacultyRq = new JSONObject();
        createFacultyRq.put("name",MOCK_FACULTY_NAME);
        createFacultyRq.put("color",MOCK_FACULTY_COLOR);

        Faculty faculty = new Faculty();
        faculty.setId(MOCK_FACULTY_ID);
        faculty.setName(MOCK_FACULTY_NAME);
        faculty.setColor(MOCK_FACULTY_COLOR);


        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(createFacultyRq.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
    }

    @Test
    public void getFacultyById() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(MOCK_FACULTY_ID);
        faculty.setName(MOCK_FACULTY_NAME);
        faculty.setColor(MOCK_FACULTY_COLOR);


        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR))
                .andExpect(jsonPath("$.id").value(MOCK_FACULTY_ID));
    }

    @Test
    public void updateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(MOCK_FACULTY_ID);
        faculty.setName(MOCK_FACULTY_NAME);
        faculty.setColor(MOCK_FACULTY_COLOR);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        faculty.setName(MOCK_FACULTY_OTHER_NAME);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        JSONObject updateFacultyRq = new JSONObject();
        updateFacultyRq.put("id",MOCK_FACULTY_ID);
        updateFacultyRq.put("name",MOCK_FACULTY_NAME);
        updateFacultyRq.put("color",MOCK_FACULTY_COLOR);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(updateFacultyRq.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_FACULTY_ID))
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_OTHER_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
    }

    @Test
    public void deleteFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(MOCK_FACULTY_ID);
        faculty.setName(MOCK_FACULTY_NAME);
        faculty.setColor(MOCK_FACULTY_COLOR);;

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllFaculties() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(MOCK_FACULTY_ID);
        faculty.setName(MOCK_FACULTY_NAME);
        faculty.setColor(MOCK_FACULTY_COLOR);

        List<Faculty> Faculties = Collections.singletonList(faculty);

        when(facultyRepository.findAll()).thenReturn(Faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Faculties)));
    }

    @Test
    public void getAllFacultiesByNameColor() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Faculty name");
        faculty.setColor("Faculty color");

        List<Faculty> Faculties = Collections.singletonList(faculty);

        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(any(String.class),any(String.class)))
                .thenReturn(Faculties);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/find?nameOrColor=" + MOCK_FACULTY_COLOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Faculties)));
    }


}
package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.FacultyServiceImpl;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.sql.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FacultyController.class)
class FacultyControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    FacultyServiceImpl facultyServiceImpl;
    @MockBean
    StudentServiceImpl studentServiceImpl;
    @MockBean
    FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyController facultyController;
    @Test
    void createTest() throws Exception {
        //Подготовка входных данных
        String name = "Gryffindor";
        String color = "red";
        Faculty facultyForCreate = new Faculty();
        facultyForCreate.setName(name);
        facultyForCreate.setColor(color);

        String request = objectMapper.writeValueAsString(facultyForCreate);

        //Подготовка ожидаемого результата
        long id = 1L;
        Faculty facultyAfterCreate = new Faculty();
        facultyAfterCreate.setName(name);
        facultyAfterCreate.setColor(color);
        facultyAfterCreate.setId(id);

        when(facultyServiceImpl.addFaculty(facultyForCreate)).thenReturn(facultyAfterCreate);
    }

    @Test
    void getTest() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty facultyForCreate = new Faculty();
        facultyForCreate.setName(name);
        facultyForCreate.setColor(color);
        facultyForCreate.setId(id);

        when(facultyServiceImpl.getFaculty(id)).thenReturn(facultyForCreate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)) //send
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(facultyForCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(facultyForCreate.getColor()))
                .andReturn();
    }

    @Test
    void updateTest() throws Exception {
        //Подготовка входных данных
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty facultyForUpdate = new Faculty();
        facultyForUpdate.setName(name);
        facultyForUpdate.setColor(color);
        facultyForUpdate.setId(id);

        String request = objectMapper.writeValueAsString(facultyForUpdate);

        //Подготовка ожидаемого результата
        when(facultyServiceImpl.updateFaculty(id, facultyForUpdate)).thenReturn(facultyForUpdate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/" + id) //send
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(facultyForUpdate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(facultyForUpdate.getColor()))
                .andReturn();
    }

    @Test
    void deleteTest() throws Exception {
        //Подготовка входных данных
        String name = "Gryffindor";
        String color = "red";
        Long id = 1L;
        Faculty facultyForDelete = new Faculty();
        facultyForDelete.setName(name);
        facultyForDelete.setColor(color);
        facultyForDelete.setId(id);

        String request = objectMapper.writeValueAsString(facultyForDelete);



        mockMvc.perform(MockMvcRequestBuilders
                .delete("/faculty/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void testGetFacultyByColorOrNamTest() throws Exception {
        //Подготовка входных данных
        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("Griffindor");
        faculty1.setColor("Red");

        Faculty faculty2 = new Faculty();
        faculty2.setId(2L);
        faculty2.setName("Sliserin");
        faculty2.setColor("Green");

        List<Faculty> faculties = Arrays.asList(faculty1,faculty2);
        //Подготовка ожидаемого результата

        when(facultyServiceImpl.findByColorIgnoreCaseOrNameIgnoreCase("Red", "Griffindor")).thenReturn(faculties);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?color=Red"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?name=Griffindor"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getFacultyByStuIdTest() throws Exception {
        //Подготовка ожидаемого результата
        Faculty faculty = new Faculty("Griffindor", "Red");
        faculty.setId(1L);
        Student student = new Student();
        student.setId(1L);
        student.setFaculty(faculty);
        long studentId  = student.getId();

        when(studentServiceImpl.getFacultyByStudentId(studentId)).thenReturn(faculty);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty//faculty-by-student-id/" + studentId)) //send
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(faculty.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(faculty.getColor()))
                .andReturn();
    }
}



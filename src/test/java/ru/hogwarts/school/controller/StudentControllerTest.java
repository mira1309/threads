package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    StudentServiceImpl studentServiceImpl;
    @MockBean
    StudentRepository studentRepository;
    @InjectMocks
    private StudentController studentController;

    @Test
    void create() throws Exception {
        //Подготовка входных данных
        String name = "Hermione";
        int age = 15;
        Student studentForCreate = new Student();
        studentForCreate.setName(name);
        studentForCreate.setAge(age);

        String request = objectMapper.writeValueAsString(studentForCreate);

        //Подготовка ожидаемого результата
        long id = 1L;
        Student studentAfterCreate = new Student();
        studentAfterCreate.setName(name);
        studentAfterCreate.setAge(age);
        studentAfterCreate.setId(id);

        when(studentServiceImpl.addStudent(studentForCreate)).thenReturn(studentAfterCreate);
    }

    @Test
    void get() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Hermione";
        int age = 15;
        long id = 1l;
        Student studentForGet = new Student();
        studentForGet.setName(name);
        studentForGet.setAge(age);
        studentForGet.setId(id);

        when(studentServiceImpl.getStudent(id)).thenReturn(studentForGet);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/" + id)) //send
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentForGet.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentForGet.getAge()))
                .andReturn();
    }

    @Test
    void update() throws Exception {
        //Подготовка входных данных
        String name = "Hermione";
        int age = 15;
        long id = 1l;
        Student studentForUpdate = new Student();
        studentForUpdate.setName(name);
        studentForUpdate.setAge(age);
        studentForUpdate.setId(id);

        String request = objectMapper.writeValueAsString(studentForUpdate);

        //Подготовка ожидаемого результата
        when(studentServiceImpl.updateStudent(studentForUpdate)).thenReturn(studentForUpdate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/students/" + id) //send
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentForUpdate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentForUpdate.getAge()))
                .andReturn();
    }

    @Test
    void delete() throws Exception {
        //Подготовка входных данных
        String name = "Hermione";
        int age = 15;
        long id = 1l;
        Student studentForDelete = new Student();
        studentForDelete.setName(name);
        studentForDelete.setAge(age);
        studentForDelete.setId(id);

        String request = objectMapper.writeValueAsString(studentForDelete);

        //ачало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    void getStudentByFacIdTest() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Hermione";
        int age = 15;
        long id = 1L;
        Faculty faculty = new Faculty("Griffindor", "Red");
        faculty.setId(1L);
        Student studentForGet = new Student();
        studentForGet.setName(name);
        studentForGet.setAge(age);
        studentForGet.setId(id);
        studentForGet.setFaculty(faculty);

        List<Student> students = Arrays.asList(studentForGet);

        //подготовка ожидаемого результата
        when(studentServiceImpl.findStudentsByFacultyId(faculty.getId())).thenReturn(students);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/students-by-faculty-id?facultyId=" + faculty.getId())) //send
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Hermione"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(15))
                .andReturn();
    }


    @Test
    void getByAgeBetween() throws Exception {
        // given
        int ageFrom = 5;
        int ageTo = 30;
        Student student = new Student();
        student.setName("Ivan");
        student.setAge(21);
        student.setId(1L);

        String request = objectMapper.writeValueAsString(student);

        when(studentServiceImpl.findWhenAgeBetween(ageFrom, ageTo)).thenReturn(Collections.singletonList(student));

        mockMvc.perform(MockMvcRequestBuilders
                          .get("/students/age/?min=" + ageFrom + "&max=" + ageTo)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(student.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(student.getAge()))
                .andDo(print());
    }
}


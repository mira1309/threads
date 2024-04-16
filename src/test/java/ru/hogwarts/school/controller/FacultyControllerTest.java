package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Faculty;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testGetFaculty(){

        //подготовка входных данных
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setColor("red");

        //подготовка ожидаемого результата
        Faculty expectedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        long id = expectedFaculty.getId();

        // начало теста
        Faculty actualFaculty = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        assertNotNull(actualFaculty);
        assertEquals(expectedFaculty, actualFaculty);

    }

    @Test
    public void testPostFaculty() {
        //Подготовка входных данных
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setColor("red");

        //Подготовка ожидаемого результата
        Faculty expectedFaculty = new Faculty();
        expectedFaculty.setName("Griffindor");
        expectedFaculty.setColor("red");

        //начало теста
        Faculty actualFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertNotNull(actualFaculty);
        expectedFaculty.setId(actualFaculty.getId());
        assertEquals(expectedFaculty, actualFaculty);
    }

    @Test
    public void testUpdateFaculty(){
        //подготовка входных данных
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setColor("red");

        // подготовка ожидаемого результата
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        long id = postedFaculty.getId();

        Faculty facultyForUpdate = new Faculty();
        facultyForUpdate.setName("Griffindor");
        facultyForUpdate.setColor("red");
        facultyForUpdate.setId(id);
        // начало теста
        this.restTemplate.put("http://localhost:" + port + "/faculty", facultyForUpdate);
        Faculty actualFaculty = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        assertNotNull(actualFaculty);
        assertEquals(facultyForUpdate, actualFaculty);
    }

    @Test
    public void testDeleteFaculty (){
        //Подготовка входных данных
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setColor("red");
        //Подготовка ожидаемого результата
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        long id = postedFaculty.getId();

        //Начало теста
        Faculty facultyForDelete = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        assertNotNull(facultyForDelete);
        assertEquals(postedFaculty, facultyForDelete);
        this.restTemplate.delete("http://localhost:" + port + "/faculty/" + id);
        Faculty facultyAfterDelete = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        assertNull(facultyAfterDelete.getId());
        assertNull(facultyAfterDelete.getColor());
        assertNull(facultyAfterDelete.getName());
    }
}



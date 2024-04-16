package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudent(){

        //подготовка входных данных
        Student student = new Student();
        student.setName("Ron");
        student.setAge(15);

        //подготовка ожидаемого результата
        Student expectedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/students", student, Student.class);
        long id = expectedStudent.getId();

        // начало теста
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/students/" + id, Student.class);
        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);

    }

    @Test
    public void testPostStudent() {
        //Подготовка входных данных
        Student student = new Student();
        student.setName("Ron");
        student.setAge(15);

        //Подготовка ожидаемого результата
        Student expectedStudent = new Student();
        expectedStudent.setName("Ron");
        expectedStudent.setAge(15);

        //начало теста
        Student actualStudent = this.restTemplate.postForObject("http://localhost:" + port + "/students", student, Student.class);
        assertNotNull(actualStudent);
        expectedStudent.setId(actualStudent.getId());
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void testUpdateStudent(){
        //подготовка входных данных
        Student student = new Student();
        student.setName("Ron");
        student.setAge(15);

        // подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/students", student, Student.class);

        long id = postedStudent.getId();

        Student studentForUpdate = new Student();
        studentForUpdate.setName("Ron");
        studentForUpdate.setAge(15);
        studentForUpdate.setId(id);
        // начало теста
        this.restTemplate.put("http://localhost:" + port + "/students", studentForUpdate);
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/students/" + id, Student.class);
        assertNotNull(actualStudent);
        assertEquals(studentForUpdate, actualStudent);
    }

    @Test
    public void testDeleteStudent(){
        //Подготовка входных данных
        Student student = new Student();
        student.setName("Ron");
        student.setAge(15);
        //Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/students", student, Student.class);
        long id = postedStudent.getId();

        //Начало теста
        Student studentForDelete = this.restTemplate.getForObject("http://localhost:" + port + "/students/" + id, Student.class);
        assertNotNull(studentForDelete);
        assertEquals(postedStudent, studentForDelete);
        this.restTemplate.delete("http://localhost:" + port + "/students/" + id);
        Student studentAfterDelete = this.restTemplate.getForObject("http://localhost:" + port + "/students/" + id, Student.class);
        assertNull(studentAfterDelete);
    }
}


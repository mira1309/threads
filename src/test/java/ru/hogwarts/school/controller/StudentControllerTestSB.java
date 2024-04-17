package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestSB {
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

    @Test
    public void testGetStudentByFaculty() {
        Long facultyId = 1L;
        String name = "Garry";
        int age = 15;
        Faculty faculty = new Faculty("Griffindor", "red");
        faculty.setId(facultyId);
        Student student = new Student();
        student.setId(1L);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(student);

        ResponseEntity<List<Student>> response = restTemplate.exchange(
                "http://localhost:" + port + "/students/students-by-faculty-id?facultyId=" + facultyId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {},
                facultyId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Student> actualStudents = response.getBody();
        assertThat(actualStudents).isEqualTo(expectedStudents);
    }
}


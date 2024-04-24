package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age/")
    public List<Student> findWhenAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findWhenAgeBetween(min, max);
    }

    @GetMapping("/students-by-faculty-id")
    public List<Student> getStudentsByFacultyId(@RequestParam Long facultyId) {
        return studentService.findStudentsByFacultyId(facultyId);
    }
    @GetMapping("/count-of-student")
    public int countOfStudent(){
        return studentService.countOfStudent();
    }

    @GetMapping("/avg-age")
    public double getAvgAge(){
        return studentService.getAvgAge();
    }

    @GetMapping("/get-last-five")
    public List<Student> getLastFiveOrderByIdDesc(){
        return studentService.getLastFiveOrderByIdDesc();
    }
}

package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;
    private final StudentService studentService;

    public FacultyController(FacultyService facultyService, StudentService studentService) {
        this.facultyService = facultyService;
        this.studentService = studentService;
    }
    @GetMapping("/{id}")
    public Faculty getFaculty(@PathVariable Long id){
        return facultyService.getFaculty(id);
    }
    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.addFaculty(faculty);
    }
    @PutMapping("/{id}")
    public Faculty updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(id,faculty);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity removeFaculty(@PathVariable Long id){
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> findFaculty(@RequestParam(required = false) String color,
                                                           @RequestParam(required = false) String name){
        if ((color != null && !color.isBlank()) || (name != null && !name.isBlank())){
            return ResponseEntity.ok(facultyService.findByColorIgnoreCaseOrNameIgnoreCase(color, name));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/faculty-by-student-id/{id}")
    public Faculty  getFacultyByStudentId(@PathVariable Long id) {
        return studentService.getFacultyByStudentId(id);
    }
}


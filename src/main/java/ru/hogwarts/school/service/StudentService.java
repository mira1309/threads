package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    Student addStudent (Student student);

    Student getStudent (Long id);

    Student updateStudent (Student student);

    void removeStudent (Long id);

    List<Student> findWhenAgeBetween(int min, int max);

    List<Student> findStudentsByFacultyId(Long facultyId);

    Faculty getFacultyByStudentId(Long id);

    int countOfStudent();

    double getAvgAge();

    List<Student> getLastFiveOrderByIdDesc();

    void printStudents();

    void printStudentsSync();
}

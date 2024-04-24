package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student addStudent (Student student);

    Student getStudent (Long id);


    Student updateStudent (Student student);

    void removeStudent (Long id);

    List<Student> findWhenAgeBetween(int min, int max);

    //получить студентов факультета
    List<Student> findStudentsByFacultyId(Long facultyId);
    Faculty getFacultyByStudentId(Long id);

    int countOfStudent();
    double getAvgAge();

    List<Student> getLastFiveOrderByIdDesc();
}

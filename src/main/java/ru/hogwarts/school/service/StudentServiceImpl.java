package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findStudentById(id);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void removeStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> findWhenAgeBetween(int min, int max) {
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public List<Student> findStudentsByFacultyId(Long facultyId) {
        return studentRepository.findStudentsByFacultyId(facultyId);
    }

    public Faculty getFacultyByStudentId(Long id) {
        /*Student student = studentRepository.findById(id).orElseThrow(
                () -> new StudentNotFoundException("Student not found with id: " + id));
        return student.getFaculty();*/
        return studentRepository.findById(id).orElseThrow(
                () -> new StudentNotFoundException("Student not found with id: " + id)).getFaculty();
    }

     public int countOfStudent(){
         return studentRepository.countOfStudent();
    }

    public double getAvgAge() {
        return studentRepository.getAvgAge();
    }
    public List<Student> getLastFiveOrderByIdDesc(){
        return studentRepository.getLastFiveOrderByIdDesc();
    }
}





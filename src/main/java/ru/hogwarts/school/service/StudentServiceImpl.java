package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
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
        return studentRepository.findById(id).get();
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
    public List<Student> findWhenAgeBetween(Integer min, Integer max) {
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public List<Student> findStudentsByFacultyId(Long facultyId) {
        return studentRepository.findStudentsByFacultyId(facultyId);
    }

    public List<Student> findFacultyByStudentIdOrName(Long id,String name) {
        return studentRepository.findByIdOrName(id, name);
    }

}





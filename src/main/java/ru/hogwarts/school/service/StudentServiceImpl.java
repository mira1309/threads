package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("addStudent method was invoked");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        logger.debug("getStudent method was invoked");
        return studentRepository.findStudentById(id);
    }

    @Override
    public Student updateStudent(Student student) {
        logger.warn("updateStudent method was invoked");
        return studentRepository.save(student);
    }

    @Override
    public void removeStudent(Long id) {
        logger.info("removeStudent method was invoked");
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> findWhenAgeBetween(int min, int max) {
        logger.info("findWhenAgeBetween method was invoked");
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public List<Student> findStudentsByFacultyId(Long facultyId) {
        logger.info("findStudentsByFacultyId method was invoked");
        return studentRepository.findStudentsByFacultyId(facultyId);
    }

    public Faculty getFacultyByStudentId(Long id) {
        logger.info("findFacultyByStudentId method was invoked");
        return studentRepository.findById(id).orElseThrow(
                () ->  new StudentNotFoundException("Student not found with id: " + id)).getFaculty();
    }

     public int countOfStudent(){
        logger.info("countOfStudent method was invoked");
        return studentRepository.countOfStudent();
    }

    public double getAvgAge() {
        logger.info("getAvgAge method was invoked");
        return studentRepository.getAvgAge();
    }
    public List<Student> getLastFiveOrderByIdDesc(){
        logger.info("getLastFiveOrderByIdDesc method was invoked");
        return studentRepository.getLastFiveOrderByIdDesc();
    }
}





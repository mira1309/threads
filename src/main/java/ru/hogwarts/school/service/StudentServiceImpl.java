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

    /*первые два имени вывести в основном потоке
    имена третьего и четвертого студента вывести в параллельном потоке
    имена пятого и шестого студента вывести в еще одном параллельном потоке.*/

    public void printStudents() {
        List<Student> students = studentRepository.findAll();

        printStudent(students.get(0));
        printStudent(students.get(1));

        Thread thread1 = new Thread(() -> {
            printStudent(students.get(2));
            printStudent(students.get(3));
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        });
        thread2.start();
    }

    /*синхронизированный метод :

    первые два имени вывести в основном потоке
    имена третьего и четвертого студента в параллельном потоке
    имена пятого и шестого студента в еще одном параллельном потоке*/
    public void printStudentsSync() {
        List<Student> students = studentRepository.findAll();

        printStudentSync(students.get(0));
        printStudentSync(students.get(1));

        Thread thread1 = new Thread(() -> {
            printStudentSync(students.get(2));
            printStudentSync(students.get(3));
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            printStudentSync(students.get(4));
            printStudentSync(students.get(5));
        });
        thread2.start();
    }

    private void printStudent(Student student) {
        logger.info("Thread: {}. Student: {}", Thread.currentThread(), student);
    }

    private synchronized void printStudentSync(Student student) {
        printStudent(student);
    }
}





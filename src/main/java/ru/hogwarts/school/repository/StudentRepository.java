package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAgeBetween(int min, int max);

    List<Student> findStudentsByFacultyId(Long facultyId);

    Student findStudentById(Long id);

 //   Используйте аннотацию @Query и добавьте следующий функционал в проект:

 //           - Возможность получить количество всех студентов в школе. Эндпоинт должен вернуть число.

    @Query(value="select count(*) from student", nativeQuery = true)
    int countOfStudent();

    @Query(value = "select avg(age) as avg from student", nativeQuery = true)
    double getAvgAge();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    List<Student> getLastFiveOrderByIdDesc();
}

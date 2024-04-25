package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("addFaculty method was invoked");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) {
        logger.info("getFaculty method was invoked");
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        logger.info("updateFaculty method was invoked");
        return facultyRepository.save(faculty);
    }

    @Override
    public void removeFaculty(Long id) {
        logger.info("removeFaculty method was invoked");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name) {
        logger.info("findByColorIgnoreCaseOrNameIgnoreCase method was invoked");
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }
}

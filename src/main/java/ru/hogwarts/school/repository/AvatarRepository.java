package ru.hogwarts.school.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Avatar;

import java.util.List;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    @Override
    List<Avatar> findAll();

}

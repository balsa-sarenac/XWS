package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.Mark;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    Mark getById(Long id);
    Mark getByName(String name);
    Mark getByNameAndId(String name, Long id);
}

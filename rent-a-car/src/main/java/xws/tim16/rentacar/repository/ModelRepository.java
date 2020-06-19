package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Model getByName(String name);
    Model getById(Long id);

}

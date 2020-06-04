package xws.team16.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.carservice.model.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Model getByName(String name);
    Model getById(Long id);

}

package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.Gearbox;

@Repository
public interface GearboxRepository extends JpaRepository<Gearbox, Long> {

    Gearbox getByType(String type);
    Gearbox getById(Long id);
}

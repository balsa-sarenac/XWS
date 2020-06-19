package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.Fuel;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long> {

    Fuel getByType(String type);
    Fuel getById(Long id);
}

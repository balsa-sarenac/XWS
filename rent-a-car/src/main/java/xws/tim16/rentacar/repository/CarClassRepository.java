package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.CarClass;

@Repository
public interface CarClassRepository extends JpaRepository<CarClass, Long> {

    CarClass getByName(String name);
    CarClass getById(Long id);
}

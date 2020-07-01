package xws.tim16.rentacar.repository;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.Car;
import xws.tim16.rentacar.model.Occupied;

import java.util.List;

@Repository
public interface OccupiedRepository extends JpaRepository<Occupied, Long> {
    List<Occupied> findAllByCarAndDateToAfter(Car c, LocalDate now);

    List<Occupied> findAllByCarId(Long carId);
}

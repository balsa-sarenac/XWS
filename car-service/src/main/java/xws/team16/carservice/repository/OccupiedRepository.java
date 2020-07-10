package xws.team16.carservice.repository;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.carservice.model.Car;
import xws.team16.carservice.model.Occupied;

import java.util.List;

@Repository
public interface OccupiedRepository extends JpaRepository<Occupied, Long> {
    List<Occupied> findAllByCarId(Long carId);

    List<Occupied> findAllByCarAndDateToAfter(Car c, LocalDate now);
}

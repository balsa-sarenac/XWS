package xws.team16.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.carservice.model.Car;
import xws.team16.carservice.model.User;

import java.util.List;
import java.util.Set;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByOwnerId(Long i);
    Set<Car> getAllByOwner(User owner);
    Set<Car> findAllByOwner(User owner);
    List<Car> findAllByOwner_Id(Long id);
}

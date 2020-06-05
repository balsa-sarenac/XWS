package xws.team16.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.carservice.model.Occupied;

@Repository
public interface OccupiedRepository extends JpaRepository<Occupied, Long> {
}

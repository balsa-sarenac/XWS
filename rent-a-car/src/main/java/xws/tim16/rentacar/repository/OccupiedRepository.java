package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.Occupied;

@Repository
public interface OccupiedRepository extends JpaRepository<Occupied, Long> {
}

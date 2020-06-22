package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.tim16.rentacar.model.RentBundle;

public interface RentBundleRepository extends JpaRepository<RentBundle, Long> {
}

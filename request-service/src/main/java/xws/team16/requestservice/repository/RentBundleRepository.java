package xws.team16.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.team16.requestservice.model.RentBundle;

public interface RentBundleRepository extends JpaRepository<RentBundle, Long> {
}

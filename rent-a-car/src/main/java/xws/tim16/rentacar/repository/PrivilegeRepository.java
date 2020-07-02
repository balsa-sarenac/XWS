package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.tim16.rentacar.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}

package xws.team16.securityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.team16.securityservice.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String privilege_rent);
}

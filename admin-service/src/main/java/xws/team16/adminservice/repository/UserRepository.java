package xws.team16.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.adminservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

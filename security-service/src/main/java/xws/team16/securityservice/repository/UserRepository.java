package xws.team16.securityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.team16.securityservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

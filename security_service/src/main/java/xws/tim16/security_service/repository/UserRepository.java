package xws.tim16.security_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.security_service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

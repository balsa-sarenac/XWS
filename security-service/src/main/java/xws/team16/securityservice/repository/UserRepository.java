package xws.team16.securityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.team16.securityservice.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAllByStatus(int i);
}

package xws.team16.securityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.team16.securityservice.model.User;

import java.sql.Timestamp;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAllByStatusAndLastPasswordResetDateAfter(int i, Timestamp time);

    List<User> findByLastPasswordResetDate(Timestamp timestamp);
}

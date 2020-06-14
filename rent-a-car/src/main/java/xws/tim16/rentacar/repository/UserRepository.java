package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.tim16.rentacar.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
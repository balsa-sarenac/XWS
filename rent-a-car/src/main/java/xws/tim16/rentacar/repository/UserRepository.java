package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.tim16.rentacar.model.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAllByStatus(int i);
}
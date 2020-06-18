package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.exception.NotFoundException;
import xws.tim16.rentacar.model.User;
import xws.tim16.rentacar.repository.UserRepository;

@Service @Slf4j
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String bax) {
        log.info("User service - get user by username");
        return this.userRepository.findByUsername(bax); // .orElseThrow(() -> new NotFoundException("User with given username was not found."));
    }
}

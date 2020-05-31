package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.User;
import xws.team16.carservice.repository.UserRepository;

@Service @Slf4j
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String bax) {
        log.info("User service - get user by username");

        return this.userRepository.findByUsername(bax).orElseThrow(() -> new NotFoundException("User with given username was not found."));
    }
}

package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.UserDTO;
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

    public User getUserById(Long id){
        log.info("User service - get user by id " + id);
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " was not found."));
    }

    public ResponseEntity<?> addUser(UserDTO userDTO) {
        log.info("User service - add user");
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEnabled(userDTO.isEnabled());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        this.userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

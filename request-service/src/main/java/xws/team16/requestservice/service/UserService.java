package xws.team16.requestservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.requestservice.dto.UserDTO;
import xws.team16.requestservice.exceptions.NotFoundException;
import xws.team16.requestservice.model.User;
import xws.team16.requestservice.repository.UserRepository;

@Service @Slf4j
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        log.info("User service - get user by id");
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with given id was not found"));
    }


    public ResponseEntity<?> addUser(UserDTO userDTO) {
        log.info("User service - add user");
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        this.userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

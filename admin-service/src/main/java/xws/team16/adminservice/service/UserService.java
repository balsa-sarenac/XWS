package xws.team16.adminservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.adminservice.exception.NotFoundException;
import xws.team16.adminservice.model.User;
import xws.team16.adminservice.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with given id was not found"));
    }
}

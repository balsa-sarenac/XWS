package xws.team16.adminservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xws.team16.adminservice.client.SecurityClient;
import xws.team16.adminservice.dto.UserByUsernameDTO;
import xws.team16.adminservice.exception.NotFoundException;
import xws.team16.adminservice.model.User;
import xws.team16.adminservice.repository.UserRepository;

import java.util.Objects;

@Service @Slf4j
public class UserService {

    private UserRepository userRepository;
    private SecurityClient securityClient;

    @Autowired
    public UserService(UserRepository userRepository, SecurityClient securityClient) {
        this.userRepository = userRepository;
        this.securityClient = securityClient;
    }

    public User getUserById(Long userId) {
        log.info("User service - get user by id");
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with given id was not found"));
    }

    /**
     * Returns user which send the request from token
     * @return User object
     */
    public User getUser() {
        log.info("Get user from username/auth");
        // get username from auth and send request to SecurityService to give you his id
        // or add username field to user class and find it directly here
        User user = null;
        try {
            ResponseEntity<UserByUsernameDTO> responseEntity = this.securityClient.getUser(
                    SecurityContextHolder.getContext().getAuthentication().getName());
            user = this.getUserById(Objects.requireNonNull(responseEntity.getBody()).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}

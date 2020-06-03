package xws.team16.requestservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.requestservice.exceptions.NotFoundException;
import xws.team16.requestservice.model.RegisteredUser;
import xws.team16.requestservice.repository.RegisteredUserRepository;

@Service @Slf4j
public class RegisteredUserService {

    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    public RegisteredUserService(RegisteredUserRepository registeredUserRepository) {
        this.registeredUserRepository = registeredUserRepository;
    }

    public RegisteredUser findUserById(Long userId) {
        return this.registeredUserRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with given id was not found"));
    }
}

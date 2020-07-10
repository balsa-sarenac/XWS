package xws.team16.carservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.carservice.dto.UserDTO;
import xws.team16.carservice.service.UserService;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserContoller {

    private UserService userService;

    @Autowired
    public UserContoller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    private ResponseEntity<?> addUser(@RequestBody UserDTO userDTO){
        log.info("User controller - adding user");
        return this.userService.addUser(userDTO);
    }
}

package xws.tim16.rentacar.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xws.tim16.rentacar.dto.UserDTO;
import xws.tim16.rentacar.security.auth.JwtAuthenticationRequest;
import xws.tim16.rentacar.service.CustomUserDetailsService;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping(produces = "application/json")
public class AuthenticationController {
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthenticationController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @SneakyThrows
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest, HttpServletResponse httpServletResponse) {
        log.info("Controller /login reached by user " + jwtAuthenticationRequest.getUsername() + " for token verification.");
        return this.userDetailsService.login(jwtAuthenticationRequest, httpServletResponse);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        log.info("Controller /register reached by user: " + userDTO.getUsername());
        return this.userDetailsService.register(userDTO);
    }

    /**
     * Enables user to log in to application
     * @param userId id of the user
     * @return 200 ok if successful or 404 not found if user does not exist
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/enable/{userId}")
    public ResponseEntity<Void> enable(@PathVariable Long userId) {
        log.info("Controller /enable reached by user id " + userId);
        this.userDetailsService.enable(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Disables user to log in to application
     * @param userId id of the user
     * @return 200 ok if successful or 404 not found if user does not exist
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/disable/{userId}")
    public ResponseEntity<Void> disable(@PathVariable Long userId) {
        log.info("Controller /disable reached by user id " + userId);
        this.userDetailsService.disable(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Verifies users authentication token
     * @return true if token is valid
     */
    @PostMapping(value = "/verify")
    public ResponseEntity<?> verify(@RequestBody String token) {
        log.info("Controller /verify invoked with token - " + token);
        return this.userDetailsService.verify(token);
    }
}

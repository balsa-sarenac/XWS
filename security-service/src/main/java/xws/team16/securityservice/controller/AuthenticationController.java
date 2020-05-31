package xws.team16.securityservice.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.securityservice.dto.UserDTO;
import xws.team16.securityservice.security.auth.JwtAuthenticationRequest;
import xws.team16.securityservice.service.impl.CustomUserDetailsService;

import javax.servlet.http.HttpServletResponse;

@RestController @Slf4j
@RequestMapping(value = "/auth", produces = "application/json")
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
    public ResponseEntity<Void> register(@RequestBody UserDTO userDTO) {
        log.info("Controller /register reached by user: " + userDTO.getUsername());
        return this.userDetailsService.register(userDTO);
    }

    @PutMapping(value = "/enable/{userId}")
    public ResponseEntity<Void> enable(@PathVariable Long userId) {
        log.info("Controller /enable reached by user id " + userId);
        return this.userDetailsService.enable(userId);
    }

}

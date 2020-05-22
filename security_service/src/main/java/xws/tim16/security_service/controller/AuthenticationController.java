package xws.tim16.security_service.controller;

import com.sun.org.apache.regexp.internal.RE;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import xws.tim16.security_service.dto.UserDTO;
import xws.tim16.security_service.model.User;
import xws.tim16.security_service.model.UserTokenState;
import xws.tim16.security_service.security.TokenUtils;
import xws.tim16.security_service.security.auth.JwtAuthenticationRequest;
import xws.tim16.security_service.service.impl.CustomUserDetailsService;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

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

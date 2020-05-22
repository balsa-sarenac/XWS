package xws.tim16.security_service.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xws.tim16.security_service.dto.UserDTO;
import xws.tim16.security_service.model.User;
import xws.tim16.security_service.model.UserTokenState;
import xws.tim16.security_service.repository.UserRepository;
import xws.tim16.security_service.security.TokenUtils;
import xws.tim16.security_service.security.auth.JwtAuthenticationRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@Service @Slf4j
public class CustomUserDetailsService implements UserDetailsService {


    private TokenUtils tokenUtils;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(TokenUtils tokenUtils, AuthenticationManager authenticationManager,
                                    UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("Custom user repository");
        User user = userRepository.findByUsername(s);
        if(user != null)
            return user;
        else throw new UsernameNotFoundException(String.format("no user found with username '%s'", s));
    }

    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        if (authenticationManager != null) {
            log.debug("Re-authenticating user '" + username + "' for password change request.");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            log.debug("No authentication manager set. can't change Password!");
            return;
        }

        log.debug("Changing password for user '" + username + "'");

        User user = (User) loadUserByUsername(username);


        user.setPassword(passwordEncoder.encode(newPassword));
        user.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);

    }

    public ResponseEntity<Void> register(UserDTO userDTO) {
        log.info("User service - registration function");
        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .enabled(false)
                .build();
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> login(JwtAuthenticationRequest jwtAuthenticationRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {
        log.info("User service - login function reached");
        final Authentication authentication =
                authenticationManager.authenticate
                        (new UsernamePasswordAuthenticationToken(
                                jwtAuthenticationRequest.getUsername(),
                                jwtAuthenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        if (!user.isEnabled()) {
            log.error("User is not enabled and can't log in.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();
        String refresh = tokenUtils.generateRefreshToken(user.getUsername());
        String username = user.getUsername();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, username, refresh));
    }

    public ResponseEntity<Void> enable(Long userId) {
        Optional<User> optional = this.userRepository.findById(userId);
        if (!optional.isPresent()) {
            log.error("User with id " + userId + " is not present");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = optional.get();
        user.setEnabled(true);
        this.userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package xws.team16.securityservice.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xws.team16.securityservice.dto.TokenDTO;
import xws.team16.securityservice.dto.UserDTO;
import xws.team16.securityservice.security.auth.JwtAuthenticationRequest;
import xws.team16.securityservice.service.impl.CustomUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(value = "*")
@RestController @Slf4j
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
    public ResponseEntity<Void> register(@RequestBody UserDTO userDTO) {
        log.info("Controller /register reached by user: " + userDTO.getUsername());
        this.userDetailsService.register(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(value = "")
    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_AGENT')")
    public ResponseEntity<?> edit(@RequestBody UserDTO userDTO) {
        log.info("Controller /edit reached by user: " + userDTO.getUsername());
        return this.userDetailsService.edit(userDTO);
    }

    /**
     * Delete user from application
     * @param userId id of the user
     * @return 200 ok if successful or 404 not found if user does not exist
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        log.info("Controller /delete reached by user id " + userId);
        this.userDetailsService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/users")
    public ResponseEntity<?> getUsers() {
        log.info("Auth controller - get all users");
        return this.userDetailsService.getUsers();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/rentPrivilege/{privilege}/{id}")
    public ResponseEntity<?> rentPrivileges(@PathVariable Boolean privilege, @PathVariable Long id) {
        log.info("Auth controller - setting rent privileges");
        boolean flag =  this.userDetailsService.rentPrivilege(privilege, id);
        if(flag == true){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        log.info("Auth controller - get all users");
        return this.userDetailsService.getUser(username);
    }

    /**
     * Enables user to log in to application
     * @param userId id of the user
     * @return 200 ok if successful or 404 not found if user does not exist
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/enable/{userId}")
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
    @GetMapping(value = "/disable/{userId}")
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

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/approve/{id}")
    public ResponseEntity<?> approveRegRequest(@PathVariable Long id) {
        return this.userDetailsService.approveRequest(id);
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/refuse/{id}")
    public ResponseEntity<?> refuseRegRequest(@PathVariable Long id) {
        return this.userDetailsService.refuseRequest(id);
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/registration-requests")
    public ResponseEntity<?> getRegistrationRequests() {
        return this.userDetailsService.getRegistrationRequests();
    }

}

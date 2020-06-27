package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.util.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.dto.RoleDTO;
import xws.tim16.rentacar.dto.UserDTO;
import xws.tim16.rentacar.exception.NotFoundException;
import xws.tim16.rentacar.model.Privilege;
import xws.tim16.rentacar.model.Role;
import xws.tim16.rentacar.model.User;
import xws.tim16.rentacar.model.UserTokenState;
import xws.tim16.rentacar.repository.RoleRepository;
import xws.tim16.rentacar.repository.UserRepository;
import xws.tim16.rentacar.security.TokenUtils;
import xws.tim16.rentacar.security.auth.JwtAuthenticationRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService{

    private TokenUtils tokenUtils;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private RoleRepository roleRepository;

    @Autowired
    public CustomUserDetailsService(TokenUtils tokenUtils, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder,  RoleRepository roleRepository) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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

    public ResponseEntity<?> register(UserDTO userDTO) {
        log.info("User service - registration function");
        User username = this.userRepository.findByUsername(userDTO.getUsername());
        if(username != null){
            return new ResponseEntity<>("Username already in use",HttpStatus.BAD_REQUEST);
        }
        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .address(userDTO.getAddress())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .isAdmin(false)
                .enabled(false)
                .companyName("")
                .businessID("")
                .build();

        if(userDTO.getRoles().get(0).equals("ROLE_AGENT")){
            user.setCompanyName(userDTO.getCompanyName());
            user.setBusinessID(userDTO.getBusinessID());
        }else if(userDTO.getRoles().get(0).equals("ROLE_ADMIN")){
            user.setAdmin(true);
            user.setEnabled(true);
        }

        Role role = this.roleRepository.findByName(userDTO.getRoles().get(0));
        user.setRoles(new ArrayList<Role>());
        user.getRoles().add(role);
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

        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();
        String refresh = tokenUtils.generateRefreshToken(user.getUsername());
        String username = user.getUsername();
        String role = user.getRoles().iterator().next().getName();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, username, refresh, role));
    }

    public void enable(Long userId) {
        User user = getUserById(userId);
        user.setEnabled(true);
        this.userRepository.save(user);
    }

    public void disable(Long userId) {
        User user = getUserById(userId);
        user.setEnabled(false);
        this.userRepository.save(user);
    }

    private User getUserById(Long userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with given id was not found."));
    }

    public ResponseEntity<?> verify(String token) {
        log.info("Custom user details service - verify token");

        String username = tokenUtils.getUsernameFromToken(token);;
        log.info("User from token - " + username);
        User userDetails = (User) loadUserByUsername(username);;

        boolean isValid = false;
        if (token != null) {
            isValid = this.tokenUtils.validateToken(token, userDetails);
        }
        if (!isValid) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        // Add roles and permissions
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setUsername(username);
        if (userDetails != null) {
            roleDTO.setRoles(new HashSet<>());
            for (Role role : userDetails.getAuthorities()) {
                roleDTO.getRoles().add(role.getAuthority());
//                for (Privilege privilege : role.getPrivileges())
//                    roleDTO.getPrivileges().add(privilege.getName());
            }
        }

        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}

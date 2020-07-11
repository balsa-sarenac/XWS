package xws.team16.securityservice.service.impl;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
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
import xws.team16.securityservice.client.CarClient;
import xws.team16.securityservice.client.MailClient;
import xws.team16.securityservice.client.RequestClient;
import xws.team16.securityservice.dto.MailDTO;
import xws.team16.securityservice.dto.RoleDTO;
import xws.team16.securityservice.dto.UserDTO;
import xws.team16.securityservice.exception.InvalidOperationException;
import xws.team16.securityservice.exception.NotFoundException;
import xws.team16.securityservice.model.*;
import xws.team16.securityservice.repository.PrivilegeRepository;
import xws.team16.securityservice.repository.RoleRepository;
import xws.team16.securityservice.repository.UserRepository;
import xws.team16.securityservice.security.TokenUtils;
import xws.team16.securityservice.security.auth.JwtAuthenticationRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service @Slf4j
public class CustomUserDetailsService implements UserDetailsService {


    private TokenUtils tokenUtils;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;
    private ModelMapper modelMapper;

    @Autowired
    private RequestClient requestClient;
    @Autowired
    private CarClient carClient;

    @Autowired
    private MailClient mailClient;

    @Autowired
    public CustomUserDetailsService(TokenUtils tokenUtils, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, PrivilegeRepository privilegeRepository, ModelMapper modelMapper) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.modelMapper = modelMapper;
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
                .enabled(true)
                .companyName("")
                .businessID("")
                .status(0)
                .numCanReq(0)
                .build();

        user.setPrivileges(new ArrayList<>());
        if(userDTO.getRoles().get(0).equals("ROLE_AGENT")){
            user.setCompanyName(userDTO.getCompanyName());
            user.setBusinessID(userDTO.getBusinessID());
            Privilege privilege = this.privilegeRepository.findByName("POST_ADS");
            user.getPrivileges().add(privilege);
        }else if(userDTO.getRoles().get(0).equals("ROLE_ADMIN")){
            user.setAdmin(true);
        }else if(userDTO.getRoles().get(0).equals("ROLE_USER")){
            user.setEnabled(false);
            Privilege privilege = this.privilegeRepository.findByName("POST_ADS");
            user.getPrivileges().add(privilege);
        }

        Role role = this.roleRepository.findByName(userDTO.getRoles().get(0));
        user.setRoles(new ArrayList<Role>());
        user.getRoles().add(role);
        user = userRepository.save(user);
        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        this.synchronizeDatabase(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public void synchronizeDatabase(UserDTO userDTO){
        log.info("User service - calling feign car");
        try {
            this.carClient.addUser(userDTO);
            log.info("Successufully called car service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling car service");
        }

        log.info("User service - calling feign request");
        try {
            this.requestClient.addUser(userDTO);
            log.info("Successufully called request service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling request service");
        }
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
            User user) {

        return getGrantedAuthorities(getPrivileges(user.getPrivileges()));
    }

    private List<String> getPrivileges(Collection<Privilege> privilegess) {

        List<String> privileges = new ArrayList<>();
        for (Privilege item : privilegess) {
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

    public ResponseEntity<?> getUsers() {
        log.info("User service - getting all users");
        List<User> users = this.userRepository.findAllByStatusAndLastPasswordResetDateAfter(0, new Timestamp(-1));
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User u: users){
            if (u.getRoles().iterator().next().getName().equals("ROLE_USER")){
                UserDTO userDTO = modelMapper.map(u, UserDTO.class);
                userDTO.setRoles(new ArrayList<>());
                for(Role r: u.getRoles()){
                    userDTO.getRoles().add(r.getName());
                }
                for(Privilege p: u.getPrivileges()){
                    userDTO.getRoles().add(p.getName());
                }
                log.info("User service - calling feign request");
                try {
                    userDTO.setFlagPaid(this.requestClient.checkPaid(u.getId()));
                    log.info("Successufully called request service");
                } catch (FeignException.NotFound e) {
                    log.info("Error calling request service");
                }
                userDTOS.add(userDTO);
            }
        }
        log.info("User service -sending all users");
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    public void delete(Long userId) {
        User user  = this.userRepository.getOne(userId);
        user.setEnabled(false);
        user.setStatus(-1);
        log.info("User service - calling feign car");
        try {
            this.carClient.removeAds(user.getId());
            log.info("Successufully called car service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling car service");
        }
        this.userRepository.save(user);
    }

    public ResponseEntity<?> edit(UserDTO userDTO) {
        User user = this.userRepository.getOne(userDTO.getId());
        user.setCompanyName(userDTO.getCompanyName());
        user.setBusinessID(userDTO.getBusinessID());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());

        this.userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> getUser(String username) {
        User user = this.userRepository.findByUsername(username);
        UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        return  new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    public boolean rentPrivilege(Boolean privilege, Long id) {
        User user =  this.userRepository.getOne(id);
        Privilege privilegee = this.privilegeRepository.findByName("POST_ADS");
        if(privilege  == true){
            if(!user.getPrivileges().contains(privilegee)) {
                user.getPrivileges().add(privilegee);
                this.userRepository.save(user);
            }
            return true;
        }else if(privilege == false){
            if(user.getPrivileges().contains(privilegee)) {
                user.getPrivileges().remove(privilegee);
                this.userRepository.save(user);
            }
            return true;
        }else {
            return false;
        }
    }

    public ResponseEntity<?> approveRequest(Long id) {
        log.info("Approving registration request");
        User user = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with given  id was not found"));
        if (user.getLastPasswordResetDate() != null) {
            throw new InvalidOperationException("Registration request has already been processed");
        }
        user.setLastPasswordResetDate(new Timestamp(0));
        user.setEnabled(true);
        this.userRepository.save(user);

        try {
            sendMail("Registration request accepted!", "You registration request has been accepted!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> refuseRequest(Long id) {
        log.info("Refusing registration request");
        User user = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with given  id was not found"));
        if (user.getLastPasswordResetDate() != null) {
            throw new InvalidOperationException("Registration request has already been processed");
        }
        user.setLastPasswordResetDate(new Timestamp(-1));
        user.setEnabled(false);
        this.userRepository.save(user);

        try {
            sendMail("Registration request refused!", "You registration request has been refused!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> getRegistrationRequests() {
        log.info("Getting all registration requests");
        List<User> requests = this.userRepository.findByLastPasswordResetDate(null);
        if (requests.size() == 0)
            return new ResponseEntity<>(HttpStatus.OK);

        List<UserDTO> users = new ArrayList<>();
        for (User user: requests) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setUsername(user.getUsername());
            users.add(userDTO);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    public ResponseEntity<?> getEmailForUser(String username) {
        log.info("Getting email for user");
        User user = this.userRepository.findByUsername(username);
        return new ResponseEntity<>(user.getEmail(), HttpStatus.OK);
    }

    public void sendMail(String subject, String message) {
        User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        String email = user.getEmail();
        log.info(email);
        MailDTO mailDTO = new MailDTO();
        mailDTO.setEmail(email);
        mailDTO.setMessage(message);
        mailDTO.setSubject(subject);
        this.mailClient.sendMail(mailDTO);
        log.info("Mail sent");
    }
}

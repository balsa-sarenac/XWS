package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
import xws.tim16.rentacar.repository.PrivilegeRepository;
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
    private RentRequestService rentRequestService;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private AdService adService;
    private ModelMapper modelMapper;
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private BillService billService;

    @Autowired
    public CustomUserDetailsService(TokenUtils tokenUtils, AuthenticationManager authenticationManager, UserRepository userRepository, RentRequestService rentRequestService, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AdService adService, ModelMapper modelMapper, PrivilegeRepository privilegeRepository) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.rentRequestService = rentRequestService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.adService = adService;
        this.modelMapper = modelMapper;
        this.privilegeRepository = privilegeRepository;
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
            Privilege privilege = this.privilegeRepository.findByName("PRIVILEGE_RENT");
            user.getPrivileges().add(privilege);
        }else if(userDTO.getRoles().get(0).equals("ROLE_ADMIN")){
            user.setAdmin(true);
        }else if(userDTO.getRoles().get(0).equals("ROLE_USER")){
            user.setEnabled(false);
            Privilege privilege = this.privilegeRepository.findByName("PRIVILEGE_RENT");
            user.getPrivileges().add(privilege);
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
        List<User> users = this.userRepository.findAllByStatus(0);
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
                userDTO.setFlagPaid(this.billService.chekPaid(u.getId()));
                userDTOS.add(userDTO);
            }
        }

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    public void delete(Long userId) {
        User user  = this.userRepository.getOne(userId);
        user.setEnabled(false);
        user.setStatus(-1);
        this.adService.deleteAds(user.getAds());
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
        Privilege privilegee = this.privilegeRepository.findByName("PRIVILEGE_RENT");
        if(privilege  == true){
            user.getPrivileges().add(privilegee);
            this.userRepository.save(user);
            return true;
        }else if(privilege == false){
            user.getPrivileges().remove(privilegee);
            this.userRepository.save(user);
            return true;
        }else {
            return false;
        }
    }


}

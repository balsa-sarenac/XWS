package xws.tim16.security_service.security.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class JwtAuthenticationRequest {
    private String username;
    private String password;
}

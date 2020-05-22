package xws.tim16.security_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserTokenState {
    private String  accessToken;
    private Integer expiresIn;
    private String username;
    private String refreshToken;
}

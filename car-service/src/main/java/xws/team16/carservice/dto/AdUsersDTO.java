package xws.team16.carservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@NoArgsConstructor
public class AdUsersDTO {
    private Map<Long, Long> adUserMap;
}

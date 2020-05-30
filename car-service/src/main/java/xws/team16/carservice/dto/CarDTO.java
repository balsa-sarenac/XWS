package xws.team16.carservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CarDTO {
    private Long modelId;
    private Long markId;
    private Long fuelId;
    private Long carClassId;
    private Long gearboxId;
    private Long userId;
}

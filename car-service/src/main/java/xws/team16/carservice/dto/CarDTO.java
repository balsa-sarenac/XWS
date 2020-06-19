package xws.team16.carservice.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CarDTO {
    private Long id;
    private Long modelId;
    private Long markId;
    private Long fuelId;
    private Long carClassId;
    private Long gearboxId;
    private Long userId;
    private int kilometrage;
    private int numberOfChildSeats;
    private boolean hasAndroid;
}

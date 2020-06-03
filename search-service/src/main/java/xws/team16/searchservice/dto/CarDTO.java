package xws.team16.searchservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CarDTO {
    private Long id;
    private ModelDTO model;
    private MarkDTO mark;
    private FuelDTO fuel;
    private CarClassDTO carClass;
    private GearboxDTO gearbox;
    private int kilometrage;
    private int numberOfChildSeats;
    private boolean hasAndroid;
    private float overallGrade;
}

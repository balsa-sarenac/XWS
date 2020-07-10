package xws.team16.searchservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CarInfoDTO {
    private Long id;
    private ModelInfoDTO model;
    private MarkInfoDTO mark;
    private FuelDTO fuel;
    private CarClassDTO carClass;
    private GearboxDTO gearbox;
    private int kilometrage;
    private int numberOfChildSeats;
    private boolean hasAndroid;
    private float overallGrade;
    private int numberGrades;
}

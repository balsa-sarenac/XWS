package xws.tim16.rentacar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportDTO {
    private Long id;
    private double kilometrage;
    private String comment;
    private Long car_id;
}

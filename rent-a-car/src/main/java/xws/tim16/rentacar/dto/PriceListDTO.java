package xws.tim16.rentacar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriceListDTO {
    private Long id;
    private double perDay;
    private double extraKilometrage;
    private double cdw;
    private double discount;
}

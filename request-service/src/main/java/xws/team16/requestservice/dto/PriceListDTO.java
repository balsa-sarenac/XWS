package xws.team16.requestservice.dto;

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
    private double discountDays;
    private String userUsername;
}

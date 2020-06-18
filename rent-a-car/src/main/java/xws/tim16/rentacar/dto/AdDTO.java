package xws.tim16.rentacar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
@NoArgsConstructor
public class AdDTO {
    private boolean cdwAvailable;
    private String pickUpPlace;
    private DateTime fromDate;
    private DateTime toDate;
    private double allowedKilometrage;
    private CarDTO carDTO;
    private Long priceListId;
    private boolean hasAndroid;
}

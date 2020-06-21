package xws.team16.carservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
@NoArgsConstructor
public class AdInfoDTO {
    private Long id;
    private boolean cdwAvailable;
    private String pickUpPlace;
    private DateTime fromDate;
    private DateTime toDate;
    private double allowedKilometrage;
    private CarInfoDTO car;
    private Long priceListId;
    private boolean hasAndroid;
}

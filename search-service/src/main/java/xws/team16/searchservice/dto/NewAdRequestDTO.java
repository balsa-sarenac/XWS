package xws.team16.searchservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
@NoArgsConstructor
public class NewAdRequestDTO {
    private Long id;
    private boolean cdwAvailable;
    private String pickUpPlace;
    private DateTime fromDate;
    private DateTime toDate;
    private double allowedKilometrage;
    private NewCarRequestDTO carDTO;
    private Long priceListId;
}

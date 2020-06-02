package xws.team16.requestservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class RequestDTO {
    private String pickUpPlace;
    private LocalDate pickUpDate;
    private LocalDate returnDate;
    private Long adId;

}

package xws.team16.requestservice.dto;

import lombok.*;
import org.joda.time.LocalDate;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    private Long id;
    private String pickUpPlace;
    private LocalDate pickUpDate;
    private LocalDate returnDate;
    private Long adId;
    private Long bundleId;
}

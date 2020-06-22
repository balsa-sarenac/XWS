package xws.tim16.rentacar.dto;

import lombok.*;
import org.joda.time.LocalDate;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OccupiedDTO {
    private Long id;
    private LocalDate dateTo;
    private LocalDate dateFrom;
    private Long carId;
    private List<Long> adsId;
}

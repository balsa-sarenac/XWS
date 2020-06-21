package xws.tim16.rentacar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDate;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OccupiedDTO {
    private Long id;
    private LocalDate dateTo;
    private LocalDate dateFrom;
    private Long carId;
    private List<Long> adsId;
}

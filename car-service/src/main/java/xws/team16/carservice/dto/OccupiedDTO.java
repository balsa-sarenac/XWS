package xws.team16.carservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class OccupiedDTO {
    private Long id;
    private LocalDate dateTo;
    private LocalDate dateFrom;
    private Long carId;
    private List<Long> adsId;
    private CarInfoDTO car;
}

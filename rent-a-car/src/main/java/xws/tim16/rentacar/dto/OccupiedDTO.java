package xws.tim16.rentacar.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.joda.time.LocalDate;
import xws.tim16.rentacar.util.JsonJodaDateTimeSerializer;
import xws.tim16.rentacar.util.JsonJodaLocalTimeSerializer;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OccupiedDTO {
    private Long id;
    @JsonSerialize(using = JsonJodaLocalTimeSerializer.class)
    private LocalDate dateTo;
    @JsonSerialize(using = JsonJodaLocalTimeSerializer.class)
    private LocalDate dateFrom;
    private Long carId;
    private CarInfoDTO car;
    private List<Long> adsId;
}

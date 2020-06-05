package xws.team16.requestservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class OccupiedDTO {
    private Long id;
    private DateTime dateTo;
    private DateTime dateFrom;
    private Long carId;
    private List<Long> adsId;
}

package xws.tim16.rentacar.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import xws.tim16.rentacar.util.JsonJodaDateTimeSerializer;


@Getter
@Setter
@NoArgsConstructor
public class SearchDTO {
    private String pickUpPlace;
    @JsonSerialize(using = JsonJodaDateTimeSerializer.class)
    private DateTime fromDate;
    @JsonSerialize(using = JsonJodaDateTimeSerializer.class)
    private DateTime toDate;
    private MarkDTO mark;
    private ModelDTO model;
    private FuelDTO fuel;
    private GearboxDTO gearbox;
    private CarClassDTO carClass;
    private double priceFrom;
    private double priceTo;
    private double kilometrageFrom;
    private double kilometrageTo;
    private double kilometrageDrive;
    private Boolean cdw;
    private int numberOfChildSeats;
}

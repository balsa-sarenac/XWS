package xws.team16.carservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsDTO {
    private CarWithHighestGradeDTO carWithHighestGrade;
    private CarWithMostCommentsDTO carWithMostComments;
    private CarWithMostKilometersDTO carWithMostKilometers;
}

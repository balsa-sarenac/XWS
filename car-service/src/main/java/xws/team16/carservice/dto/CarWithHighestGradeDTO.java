package xws.team16.carservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarWithHighestGradeDTO {
    private Long id;
    private Long markId;
    private Long modelId;
    private String markName;
    private String modelName;
    private float averageGrade;
}

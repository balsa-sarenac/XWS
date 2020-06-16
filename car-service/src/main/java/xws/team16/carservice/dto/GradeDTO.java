package xws.team16.carservice.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GradeDTO {
    private Long id;
    private int grade;
    private String userUsername;
    private Long carId;
}

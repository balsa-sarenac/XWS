package xws.tim16.rentacar.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeDTO {
    private Long id;
    private int grade;
    private String userUsername;
    private Long carId;
    private Long adId;
}

package xws.team16.carservice.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private String text;
    private boolean approved;
    private String userUsername;
    private Long carId;
    private Long adId;
    private String role;
}

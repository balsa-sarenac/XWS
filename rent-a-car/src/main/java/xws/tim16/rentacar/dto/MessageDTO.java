package xws.tim16.rentacar.dto;

import lombok.*;
import org.joda.time.DateTime;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private String text;
    private DateTime sent;
    private String user;
    private Long companionId;
}

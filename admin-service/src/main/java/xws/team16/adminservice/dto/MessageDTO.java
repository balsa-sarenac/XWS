package xws.team16.adminservice.dto;

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
}

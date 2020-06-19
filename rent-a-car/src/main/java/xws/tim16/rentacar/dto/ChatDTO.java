package xws.tim16.rentacar.dto;

import lombok.*;

import java.util.Queue;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Queue<MessageDTO> messages;
    private UserDTO owner;
    private UserDTO companion;
}

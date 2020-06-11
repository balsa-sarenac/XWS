package xws.team16.adminservice.dto;

import lombok.*;

import java.util.Set;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Set<MessageDTO> messages;
    private UserDTO owner;
    private UserDTO companion;
}

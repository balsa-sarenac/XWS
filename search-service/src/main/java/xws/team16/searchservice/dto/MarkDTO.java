package xws.team16.searchservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class MarkDTO {
    private Long id;
    private String name;
    private List<ModelDTO> models;
}

package xws.team16.carservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private String info;
    private String type;
    private Blob image;
}

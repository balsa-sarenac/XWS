package xws.team16.requestservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BundleDTO {
    private List<RequestDTO> requests;
}

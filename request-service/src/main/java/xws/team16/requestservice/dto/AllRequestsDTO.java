package xws.team16.requestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllRequestsDTO {
    private List<RequestDTO> all;
    private List<RequestDTO> pending;
    private List<RequestDTO> paid;
    private List<RequestDTO> finished;
}

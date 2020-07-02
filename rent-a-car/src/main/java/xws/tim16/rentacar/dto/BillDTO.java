package xws.tim16.rentacar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    private Long id;
    private double price;
    private Boolean paid;
    private UserDTO user;
}

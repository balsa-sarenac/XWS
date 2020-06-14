package xws.team16.requestservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Entity
public class RentBundle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "bundle", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RentRequest> requests;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus bundleStatus;
}

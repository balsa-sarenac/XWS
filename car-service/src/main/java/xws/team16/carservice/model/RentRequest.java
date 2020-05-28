package xws.team16.carservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class RentRequest {
    @Id @Column
    private Long id;

    @Column
    private RequestStatus status;

    @Column
    private boolean bundle;

    @Column
    private Date dateCreated;

    @ManyToOne
    private Ad ad;
}

package xws.team16.carservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@Entity
public class RentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;

    @Column(name = "bundle", nullable = false)
    private boolean bundle;

    @CreatedDate
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;
}

package xws.team16.carservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Entity
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "per_day")
    private double perDay;

    @Column(name = "extra_kilometrage")
    private double extraKilometrage;

    @Column(name = "cdw")
    private double cdw;

    @Column(name = "discount")
    private double discount;
}

package xws.team16.carservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PriceList {
    @Id @Column
    private Long id;

    @Column
    private double perDay;

    @Column
    private double extraKilometrage;

    @Column
    private double cdw;

    @Column
    private double discount;
}

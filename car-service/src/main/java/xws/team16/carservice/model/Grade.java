package xws.team16.carservice.model;

import javax.persistence.*;

@Entity
public class Grade {
    @Id @Column
    private Long id;

    @Column
    private int grade;

    @ManyToOne
    private User user;

    @ManyToOne
    private Car car;
}

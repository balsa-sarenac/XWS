package xws.team16.carservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Occupied {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "date_from", nullable = false)
   private Date dateFrom;

   @Column(name = "date_to", nullable = false)
   private Date dateTo;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "car_id", nullable = false)
   private Car car;

}
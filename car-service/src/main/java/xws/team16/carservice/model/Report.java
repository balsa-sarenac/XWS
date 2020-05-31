package xws.team16.carservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Report {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "kilometrage", nullable = false)
   private double kilometrage;

   @Column(name = "comment")
   private String comment;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "car_id", nullable = false)
   private Car car;

}
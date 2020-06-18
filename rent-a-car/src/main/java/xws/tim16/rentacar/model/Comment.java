package xws.tim16.rentacar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "text", nullable = false)
   private String text;

   @Column(name = "approved", nullable = false)
   private boolean approved;

   @ManyToOne
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @ManyToOne
   @JoinColumn(name = "car_id", nullable = false)
   private Car car;

}
package xws.tim16.rentacar.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
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
   @JoinColumn(name = "ad_id", nullable = false)
   private Ad ad;

   @ManyToOne
   @JoinColumn(name = "car_id", nullable = false)
   private Car car;

   private Long refId;

}
package xws.team16.adminservice.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String text;
   private boolean approved;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "car_id", nullable = false)
   private Car car;

}
package xws.team16.carservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter @Setter @SuperBuilder
@NoArgsConstructor
@Entity
public class RegisteredUser extends User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "isBlocked")
   private boolean isBlocked;

   @Column(name = "adsPosted")
   private int adsPosted;

   @Column(name = "isAdmin")
   private boolean isAdmin;

}
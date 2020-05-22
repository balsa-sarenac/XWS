package xws.tim16.security_service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
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
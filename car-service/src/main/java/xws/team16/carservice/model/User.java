package xws.team16.carservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class User {
   @Id @Column
   private Long id;

   @Column
   private String username;

   @Column
   private String password;

   @Column
   private String firstName;

   @Column
   private String lastName;

   @Column
   private boolean isEnabled;

   @OneToMany
   private Set<Car> cars;

   @OneToMany
   private Set<Ad> ads;


}
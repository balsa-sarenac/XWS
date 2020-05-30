package xws.team16.carservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter @SuperBuilder
@NoArgsConstructor
@Entity @Table(name = "USER_TABLE") @Inheritance(strategy = InheritanceType.JOINED)
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "username")
   private String username;

   @Column(name = "password")
   private String password;

   @Column(name = "first_name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "is_enabled")
   private boolean isEnabled;

   @OneToMany(mappedBy = "owner")
   private Set<Car> cars;

   @OneToMany(mappedBy = "user")
   private Set<Ad> ads;


}
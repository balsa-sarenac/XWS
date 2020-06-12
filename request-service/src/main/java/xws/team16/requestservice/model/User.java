package xws.team16.requestservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@Entity @Table(name = "USER_TABLE")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

//   private String username;
//   private String firstName;
//   private String lastName;
//   private String companyName;
//   private String address;
//   private String businessID;

   @OneToMany(mappedBy = "user")
   private Set<Ad> ads;

   @OneToMany(mappedBy = "user")
   private Set<RentRequest> request;


   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof User)) return false;
      User user = (User) o;
      return getId().equals(user.getId());
   }

   @Override
   public int hashCode() {
      return Objects.hash(getId());
   }
}
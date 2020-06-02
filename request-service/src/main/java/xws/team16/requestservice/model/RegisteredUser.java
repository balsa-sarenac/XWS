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
@Entity
public class RegisteredUser extends User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

//   private boolean isBlocked;
//   private int adsPosted;
//   private boolean isAdmin;

   @OneToMany(mappedBy = "user")
   private Set<RentRequest> request;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof RegisteredUser)) return false;
      RegisteredUser that = (RegisteredUser) o;
      return getId().equals(that.getId());
   }

   @Override
   public int hashCode() {
      return Objects.hash(getId());
   }
}
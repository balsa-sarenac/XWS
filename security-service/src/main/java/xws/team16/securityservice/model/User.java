package xws.team16.securityservice.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@Getter @Setter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "user_table")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String username;

   @Column(nullable = false)
   private String password;

   @Column(nullable = false)
   private String firstName;

   @Column(nullable = false)
   private String lastName;

   @Column(nullable = false)
   private boolean enabled;

   @Column
   private Timestamp lastPasswordResetDate;

   @ManyToMany
   @JoinTable(name = "user_authority",
           joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
   private Set<Authority> authorities;

   @Column
   private boolean accountNonExpired;

   @Column
   private boolean accountNonLocked;

   @Column
   private boolean credentialsNonExpired;

   @Override
   public Collection<Authority> getAuthorities() {
      return authorities;
   }

   @Override
   public String getPassword() {
      return password;
   }

   @Override
   public String getUsername() {
      return username;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return enabled;
   }
}
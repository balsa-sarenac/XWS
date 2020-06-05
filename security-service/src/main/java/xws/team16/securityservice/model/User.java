package xws.team16.securityservice.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Getter @Setter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "user_table")
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

//   @Column(name = "adsPosted")
//   private int adsPosted;

   @Column(name = "isAdmin")
   private boolean isAdmin;

   @Column(name = "companyName")
   private String companyName;

   @Column(name = "address")
   private String address;

   @Column(name = "businessID")
   private String businessID;

   @Column
   private Timestamp lastPasswordResetDate;

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "users_roles",
           joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
   private Collection<Role> roles;

   @Column
   private boolean accountNonExpired;

   @Column
   private boolean accountNonLocked;

   @Column
   private boolean credentialsNonExpired;

   @Override
   public Collection<Role> getAuthorities() {
      return roles;
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
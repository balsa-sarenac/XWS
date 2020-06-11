package xws.team16.adminservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "user_table")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String username;

   @Column(nullable = false)
   private String firstName;

   @Column(nullable = false)
   private String lastName;

   @Column(name = "company_name")
   private String companyName;

   @Column(name = "address")
   private String address;

   @Column(name = "business_id")
   private String businessID;

   @OneToMany(mappedBy = "user")
   private Set<Car> cars;

   @OneToMany(mappedBy = "user")
   private Set<Comment> comments;

   @OneToMany(mappedBy = "sender")
   private Set<Message> sent;

   @OneToMany(mappedBy = "receiver")
   private Set<Message> received;

}
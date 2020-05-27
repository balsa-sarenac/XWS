package xws.team16.securityservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter @Setter @SuperBuilder
@NoArgsConstructor
@Entity
public class Agent extends User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "companyName")
   private String companyName;

   @Column(name = "address")
   private String address;

   @Column(name = "businessID")
   private String businessID;

}
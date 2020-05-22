package xws.tim16.security_service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
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
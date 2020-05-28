package xws.team16.carservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CarClass {
   @Id @Column
   private Long id;

   @Column
   private String name;

}
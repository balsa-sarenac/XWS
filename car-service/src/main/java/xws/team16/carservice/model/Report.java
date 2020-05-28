package xws.team16.carservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Report {
   @Id
   @Column
   private Long id;

   @Column
   private double kilometrage;

   @Column
   private String comment;

}
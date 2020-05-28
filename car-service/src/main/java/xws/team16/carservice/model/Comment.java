package xws.team16.carservice.model;

import javax.persistence.*;

@Entity
public class Comment {
   @Id
   @Column
   private Long id;

   @Column
   private String text;

   @Column
   private boolean approved;

   @ManyToOne
   private User user;

   @ManyToOne
   private Car car;

}
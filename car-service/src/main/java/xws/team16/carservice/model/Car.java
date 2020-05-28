package xws.team16.carservice.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Car {
   @Id
   @Column
   private Long id;

   @Column
   private double price;

   @Column
   private double kilometrage;

   @Column
   private int numberOfChildSeats;

   @Column
   private boolean hasAndroid;

   @Column
   private int numberOfGrades;

   @OneToMany
   private Set<Comment> comment;

   @ManyToOne
   private Mark mark;

   @ManyToOne
   private CarClass carClass;

   @ManyToOne
   private Fuel fuel;

   @ManyToOne
   private Model model;

   @ManyToOne
   private Gearbox gearbox;

   @OneToMany
   private Set<Occupied> occupied;

   @OneToMany
   private Set<Report> report;

   @ManyToOne
   private User user;

   @OneToMany
   private Set<Grade> grades;

}
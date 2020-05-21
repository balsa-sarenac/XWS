package xws.tim16.car_service.model;

import java.util.Set;

public class Car {
   private double price;
   private double kilometrage;
   private int numberOfChildSeats;
   private boolean hasAndroid;
   private int numberOfGrades;

   private Set<Comment> comment;
   private Mark mark;
   private CarClass carClass;
   private Set<Fuel> fuel;
   private Model model;
   private Gearbox gearbox;
   private Set<Occupied> occupied;
   private Set<Report> report;
   private User user;
   private Set<Grade> grades;

}
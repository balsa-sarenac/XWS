package xws.tim16.rentacar.model;

public class Car {
   private double price;
   private double kilometrage;
   private int numberOfChildSeats;
   private boolean hasAndroid;
   private double grade;
   private int numberOfGrades;
   
   public java.util.Collection<Comment> comment;
   public Mark mark;
   public CarClass carClass;
   public java.util.Set<Fuel> fuel;
   public Model model;
   public Gearbox gearbox;
   public java.util.Set<Occupied> occupied;
   public java.util.Set<Report> report;
   public User user;


}
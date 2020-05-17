package xws.tim16.rentacar.model;

import xws.tim16.rentacar.model.Ad;
import xws.tim16.rentacar.model.Car;
import xws.tim16.rentacar.model.RentRequest;

public class User {
   private String firstName;
   private String lastName;
   private String username;
   private String password;
   private boolean isAdmin;
   private boolean isBlocked;
   private int adsPosted;

   public java.util.Set<RentRequest> request;
   public java.util.Set<Car> cars;
   public java.util.Set<Ad> ads;


}
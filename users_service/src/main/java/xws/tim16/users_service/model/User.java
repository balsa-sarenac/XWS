package xws.tim16.users_service.model;

import java.util.Set;

public class User {
   private String username;
   private String password;
   private String firstName;
   private String lastName;
   private boolean isEnabled;

   private Set<Car> cars;
   private Set<Ad> ads;


}
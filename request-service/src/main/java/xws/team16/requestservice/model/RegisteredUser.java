package xws.tim16.request_service.model;

import java.util.Set;

public class RegisteredUser extends User {
   private boolean isBlocked;
   private int adsPosted;
   private boolean isAdmin;

   private Set<RentRequest> request;


}
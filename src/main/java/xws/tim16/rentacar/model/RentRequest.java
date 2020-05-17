package xws.tim16.rentacar.model;
import xws.tim16.rentacar.model.RequestStatus;

import java.util.Date;


public class RentRequest {
   private RequestStatus status;
   private boolean bundle;
   private Date dateCreated;

   public Ad[] ad;

}
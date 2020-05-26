package xws.team16.requestservice.model;


import java.util.Set;

public class Ad {
   private boolean cdwAvailable;
   private String pickUpPlace;
   private String pickUpDate;
   private String returnDate;
   private double allowedKilometrage;

   private Set<RentRequest> request;
   private Car car;
   // private PriceList priceList;

}
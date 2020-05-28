package xws.team16.carservice.model;


import javax.persistence.*;
import java.util.Set;
@Entity
public class Ad {
   @Id
   @Column
   private Long id;

   @Column
   private boolean cdwAvailable;

   @Column
   private String pickUpPlace;

   @Column
   private String pickUpDate;

   @Column
   private String returnDate;

   @Column
   private double allowedKilometrage;

   @OneToMany
   private Set<Car> cars;

   @OneToMany
   private Set<RentRequest> request;

   @ManyToOne
   private PriceList priceList;

}
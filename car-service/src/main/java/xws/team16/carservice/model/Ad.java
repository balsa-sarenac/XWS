package xws.team16.carservice.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Ad {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "cdw_available")
   private boolean cdwAvailable;

   @Column(name = "prick_up_place")
   private String pickUpPlace;

   @Column(name = "pick_up_date")
   private String pickUpDate;

   @Column(name = "return_date")
   private String returnDate;

   @Column(name = "allowed_kilometrage")
   private double allowedKilometrage;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "car_id", nullable = false)
   private Car car;

   @OneToMany(mappedBy = "ad")
   private Set<RentRequest> request;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "price_list_id", nullable = false)
   private PriceList priceList;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

}
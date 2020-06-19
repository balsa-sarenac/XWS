package xws.tim16.rentacar.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ad {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "cdw_available")
   private boolean cdwAvailable;

   @Column(name = "pick_up_place")
   private String pickUpPlace;

   @Column(name = "from_date")
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime", parameters = {
           @org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
           @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")
   })
   private DateTime fromDate;

   @Column(name = "to_date")
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime", parameters = {
           @org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
           @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")
   })
   private DateTime toDate;

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
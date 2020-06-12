package xws.team16.requestservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@Entity
public class RentRequest {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Enumerated(value = EnumType.STRING)
   private RequestStatus status;

   @Column(name = "date_created", nullable = false)
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime", parameters = {
           @org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
           @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")
   })
   private DateTime dateCreated;

   @Column(name = "pick_up_date")
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate", parameters = {
           @org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
           @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")
   })
   private LocalDate pickUpDate;

   @Column(name = "return_date")
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate", parameters = {
           @org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
           @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")
   })
   private LocalDate returnDate;

   @Column(name = "pick_up_place")
   private String pickUpPlace;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "ad_id", nullable = false)
   private Ad ad;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "bundle_id", nullable = true)
   private RentBundle bundle;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

}
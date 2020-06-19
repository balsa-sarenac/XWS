package xws.tim16.rentacar.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String text;

   @Column(name = "date_sent", nullable = false)
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime", parameters = {
           @org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
           @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")
   })
   private DateTime sent;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "sender_id", nullable = false)
   private User sender;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "receiver_id", nullable = false)
   private User receiver;

}
package xws.team16.carservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
public class Occupied {
   @Id
   @Column
   private Long id;

   @Column
   private Date dateFrom;

   @Column
   private Date dateTo;

}
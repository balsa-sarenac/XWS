package xws.team16.carservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Model {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name")
   private String name;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "mark_id", nullable = false)
   private Mark mark;

}
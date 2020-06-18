package xws.team16.carservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.math.raw.Mod;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Mark {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name")
   private String name;

   @OneToMany(mappedBy = "mark", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   private Set<Model> models;

}
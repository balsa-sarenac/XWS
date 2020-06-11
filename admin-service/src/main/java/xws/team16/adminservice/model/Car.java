package xws.team16.adminservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private Set<Comment> comments;
}

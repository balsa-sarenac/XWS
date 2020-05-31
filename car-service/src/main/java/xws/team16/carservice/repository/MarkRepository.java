package xws.team16.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.carservice.model.Mark;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
}

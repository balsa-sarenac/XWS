package xws.team16.carservice.repository;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.carservice.model.Ad;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findAllByCarId(Long id);

    List<Ad> findAllByUserId(Long user_id);

    List<Ad> findAllByToDateAfter(DateTime minusDays);
}

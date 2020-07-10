package xws.team16.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.carservice.model.PriceList;

import java.util.List;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    List<PriceList> findAllByUserUsername(String username);
}

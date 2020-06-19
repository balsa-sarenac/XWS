package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.PriceList;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {
}

package xws.team16.searchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.searchservice.model.PriceList;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {
}

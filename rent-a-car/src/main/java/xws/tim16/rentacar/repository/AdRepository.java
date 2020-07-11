package xws.tim16.rentacar.repository;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.Ad;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    @Query("select ad from Ad ad where ad.pickUpPlace = (?1) and ad.fromDate <= (?2) and ad.toDate >= (?3) and (ad.car.model.id = (?4) or (?4) is null) and (ad.car.mark.id = (?5) or (?5) is null) and" +
            "(ad.car.carClass.id = (?6) or (?6) is null) and (ad.car.fuel.id = (?7) or (?7) is null) and (ad.car.gearbox.id = (?8) or (?8) is null) and (ad.priceList.perDay >= (?9) and ad.priceList.perDay <= (?10)) and" +
            "(ad.car.kilometrage >=(?11) and ad.car.kilometrage <= (?12)) and (ad.allowedKilometrage <= (?13) or (?13)=0 or ad.allowedKilometrage = 0) and (ad.car.numberOfChildSeats = (?15) or (?15)=0) and (ad.cdwAvailable = (?14) or (?14) = false)" )
    Page<Ad> searchAds(String pickUpPlace, DateTime fromDate, DateTime toDate, Long modelId, Long markId, Long carClassId, Long fuelId, Long gearboxId, Double priceFrom, Double priceTo, Double kilomterageFrom, Double kilometrageTo, Double kilometrageDrive, Boolean cdw, Integer numberOfChildSeats, Pageable pageable);

    List<Ad> findAllByToDateAfter(DateTime now);

    List<Ad> findAllByCarId(Long carId);
}

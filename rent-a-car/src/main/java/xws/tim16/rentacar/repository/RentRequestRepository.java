package xws.tim16.rentacar.repository;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.RentRequest;
import xws.tim16.rentacar.model.RequestStatus;
import xws.tim16.rentacar.model.User;

import java.util.List;

@Repository
public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {

    List<RentRequest> findByAdId(Long id);

    List<RentRequest> findByUser(User user);

    List<RentRequest> findByUserAndStatus(User user, RequestStatus status);

    List<RentRequest> findByUserAndStatusAndReturnDateAfter(User user, RequestStatus status, LocalDate returnDate);

    List<RentRequest> findAllByAdCarIdAndStatus(Long carId, RequestStatus paid);

    List<RentRequest> findAllByStatusAndUserId(RequestStatus cancelled, Long id);

    List<RentRequest> findAllByAd_User_Id(Long id);

    List<RentRequest> findAllByStatus(RequestStatus pending);
}

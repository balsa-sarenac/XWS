package xws.team16.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.requestservice.model.RentRequest;
import xws.team16.requestservice.model.User;

import java.util.List;

@Repository
public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {

    List<RentRequest> findByAdId(Long id);

    List<RentRequest> findByUser(User user);
}

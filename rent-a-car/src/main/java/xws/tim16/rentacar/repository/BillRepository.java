package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.Bill;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findAllByUserUsernameAndPaid(String username, boolean b);

    List<Bill> findAllByUserIdAndPaid(Long id, boolean b);

    List<Bill> findAllByUserUsername(String username);
}

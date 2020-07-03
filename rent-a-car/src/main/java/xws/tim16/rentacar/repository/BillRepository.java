package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.tim16.rentacar.model.Bill;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findAllByUserUsernameAndPaid(String username, boolean b);

    List<Bill> findAllByUserIdAndPaid(Long id, boolean b);

    List<Bill> findAllByUserUsername(String username);
}

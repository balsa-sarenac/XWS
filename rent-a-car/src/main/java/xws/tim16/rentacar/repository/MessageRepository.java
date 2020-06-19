package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.Message;
import xws.tim16.rentacar.model.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderOrReceiverOrderBySent(User sender, User receiver);
}

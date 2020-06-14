package xws.team16.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.adminservice.model.Message;
import xws.team16.adminservice.model.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderOrReceiverOrderBySent(User sender, User receiver);
}

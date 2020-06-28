package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    List<Comment> findByCarIdAndAndApproved(Long carId, Boolean approved);

    List<Comment> findAllByApproved(boolean b);

    Comment findByUserIdAndAdId(Long user_id, Long ad_id);

}

package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.CommentDTO;
import xws.team16.carservice.model.Ad;
import xws.team16.carservice.model.Car;
import xws.team16.carservice.model.Comment;
import xws.team16.carservice.model.User;
import xws.team16.carservice.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private CarService carService;
    private AdService adService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, CarService carService, AdService adService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.carService = carService;
        this.adService = adService;
    }

    public ResponseEntity<?> createComment(CommentDTO commentDTO, Boolean reply) {
        log.info("Comment service - creating comment");
        Comment comment = Comment.builder()
                .approved(false)
                .text(commentDTO.getText())
                .build();
        User user = this.userService.getUserByUsername(commentDTO.getUserUsername());
        Ad ad = this.adService.getAd(commentDTO.getAdId());
        Car car = ad.getCar();

        comment.setCar(car);
        comment.setAd(ad);
        comment.setUser(user);

        if(commentDTO.getRole().equals("ROLE_USER") && reply == false) {
            Comment commentCheck = this.commentRepository.findByUserIdAndAdId(user.getId(), ad.getId());
            if (commentCheck != null) {
                log.info("Comment service - user comment already created");
                return new ResponseEntity<>("User already add comment for this car", HttpStatus.BAD_REQUEST);
            }
        }

        this.commentRepository.save(comment);
        log.info("Comment service - comment created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> check(String username, Long id){
        log.info("Comment service - checking comment already created");
        User user = this.userService.getUserByUsername(username);
        Comment commentCheck = this.commentRepository.findByUserIdAndAdId(user.getId(), id);

        if (commentCheck != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    public ResponseEntity<?> getComments(Long carId) {
        log.info("Comment service - get all comments for car with id" + carId);
        List<Comment> comments = this.commentRepository.findByCarIdAndAndApproved(carId, true);
        log.info("Comment service - retrieving comments");
        return new ResponseEntity<>(transformComments(comments),HttpStatus.OK);
    }

    public List<CommentDTO> transformComments(List<Comment> comments){
        List<CommentDTO> commentDTOS = new ArrayList<>();
        log.info("Comment service - creating dto for comments");
        for (Comment comment: comments){
            CommentDTO commentDTO = CommentDTO.builder()
                    .approved(comment.isApproved())
                    .id(comment.getId())
                    .text(comment.getText())
                    .carId(comment.getCar().getId())
                    .adId(comment.getAd().getId())
                    .userUsername(comment.getUser().getUsername())
                    .build();
            commentDTOS.add(commentDTO);
        }

        return commentDTOS;
    }

    public ResponseEntity<?> acceptOrRefuse(Long id, Boolean decision) {
        log.info("Comment service - accepting or refusing comment with id" + id);
        if(decision == true){
            log.info("Comment service - accepting comment");
            Comment comment = this.commentRepository.findById(id).orElseGet(null);
            if(comment == null){
                return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            comment.setApproved(true);
            this.commentRepository.save(comment);
            log.info("Comment service - successfully accepted comment");
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            log.info("Comment service - refusing comment");
            this.commentRepository.deleteById(id);
            log.info("Comment service - successfully refused comment");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getAllComments() {
        log.info("Comment service - get all comments ");
        List<Comment> comments = this.commentRepository.findAllByApproved(false);
        log.info("Comment service - retrieving comments");
        return new ResponseEntity<>(transformComments(comments),HttpStatus.OK);


    }
}

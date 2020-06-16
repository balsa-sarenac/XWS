package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.CommentDTO;
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

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, CarService carService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.carService = carService;
    }

    public ResponseEntity<?> createComment(CommentDTO commentDTO) {
        log.info("Comment service - creating comment");
        Comment comment = Comment.builder()
                .approved(false)
                .text(commentDTO.getText())
                .build();
        User user = this.userService.getUserByUsername(commentDTO.getUserUsername());
        Car car = this.carService.getCar(commentDTO.getCar().getId());
        comment.setCar(car);
        comment.setUser(user);

        this.commentRepository.save(comment);
        log.info("Comment service - comment created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> getComments(Long carId) {
        log.info("Comment service - get all comments for car with id" + carId);
        List<Comment> comments = this.commentRepository.findByCarIdAndAndApproved(carId, true);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        log.info("Comment service - creating dto for comments");
        for (Comment comment: comments){
            CommentDTO commentDTO = CommentDTO.builder()
                    .approved(comment.isApproved())
                    .id(comment.getId())
                    .text(comment.getText())
                    .userUsername(comment.getUser().getUsername())
                    .build();
            commentDTOS.add(commentDTO);
        }
        log.info("Comment service - retrieving comments");
        return new ResponseEntity<>(commentDTOS,HttpStatus.OK);
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

}

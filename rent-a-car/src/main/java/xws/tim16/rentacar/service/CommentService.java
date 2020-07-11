package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.client.CarClient;
import xws.tim16.rentacar.dto.CommentDTO;
import xws.tim16.rentacar.generated.GetCommentResponse;
import xws.tim16.rentacar.generated.PostCommentResponse;
import xws.tim16.rentacar.generated.TComment;
import xws.tim16.rentacar.model.Ad;
import xws.tim16.rentacar.model.Car;
import xws.tim16.rentacar.model.Comment;
import xws.tim16.rentacar.model.User;
import xws.tim16.rentacar.repository.AdRepository;
import xws.tim16.rentacar.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private CarService carService;
    private AdService adService;
    private CarClient carClient;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, CarService carService, AdService adService, CarClient carClient) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.carService = carService;
        this.adService = adService;
        this.carClient = carClient;
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

        if(commentDTO.getRole().equals("ROLE_AGENT")) {
            log.info("Sending soap request to car service");
            TComment tComment = new TComment();
            tComment.setApproved(false);
            tComment.setText(commentDTO.getText());
            tComment.setUserUsername(user.getUsername());
            tComment.setAdId(ad.getRefId());

            try {
                PostCommentResponse response = this.carClient.postNewComment(tComment);
                comment.setRefId(response.getCommentResponse());
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("Soap request successfully finished");
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

        List<CommentDTO> commentDTOS = new ArrayList<>();
        List<Ad> ads = this.adRepository.findAllByCarId(carId);
        for(Ad a: ads){
            if(a.getRefId() != null) {
                log.info("Sending soap request to car service");
                try {
                    GetCommentResponse response = this.carClient.getComments(a.getRefId());
                    for (TComment t : response.getComments()) {
                        commentDTOS.add(modelMapper.map(t, CommentDTO.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("Soap request successfully finished");
            }
        }
        List<CommentDTO>  commentsDTO = transformComments(comments);
        for(CommentDTO cd: commentDTOS){
            if(!commentsDTO.contains(cd)){
                commentsDTO.add(cd);
            }
        }

        log.info("Comment service - comments successfully retrieved");
        return new ResponseEntity<>(commentsDTO,HttpStatus.OK);
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

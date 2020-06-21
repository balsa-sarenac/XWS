package xws.team16.carservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.carservice.dto.CommentDTO;
import xws.team16.carservice.service.CommentService;

@RestController @Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "/comment")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    private ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO){
        log.info("Comment controller - create comment");
        return this.commentService.createComment(commentDTO);
    }


    @GetMapping(value = "/{carId}")
    private ResponseEntity<?> getComments(@PathVariable Long carId){
        log.info("Comment controller - getting comment for car");
        return this.commentService.getComments(carId);
    }

    @GetMapping(value = "/all")
    private ResponseEntity<?> getAllComments(){
        log.info("Comment controller - getting all comments");
        return this.commentService.getAllComments();
    }

    @GetMapping(value = "/accept/{id}/{decision}")
    private ResponseEntity<?> acceptOrRefuse(@PathVariable Long id, @PathVariable Boolean decision){
        log.info("Comment controller - accepting or refusing comment");
        return this.commentService.acceptOrRefuse(id, decision);
    }
}

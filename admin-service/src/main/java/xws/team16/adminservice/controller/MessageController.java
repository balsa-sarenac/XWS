package xws.team16.adminservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.adminservice.dto.MessageDTO;
import xws.team16.adminservice.service.MessageService;

@CrossOrigin
@RestController @Slf4j
@RequestMapping("/message")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getAll(@PathVariable Long userId) {
        log.info("Message controller - get all users messages");
        return this.messageService.getAll(userId);
    }

    @PostMapping
    public ResponseEntity<?> newMessage(@RequestBody MessageDTO messageDTO) {
        log.info("Message controller - new message");
        return this.messageService.newMessage(messageDTO);
    }
}

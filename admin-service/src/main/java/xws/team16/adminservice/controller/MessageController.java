package xws.team16.adminservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.adminservice.dto.MessageDTO;
import xws.team16.adminservice.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getAll(@PathVariable Long userId) {
        return this.messageService.getAll(userId);
    }

    @PostMapping
    public ResponseEntity<?> newMessage(@RequestBody MessageDTO messageDTO) {
        return this.messageService.newMessage(messageDTO);
    }
}

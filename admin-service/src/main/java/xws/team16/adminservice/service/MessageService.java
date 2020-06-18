package xws.team16.adminservice.service;

import https.ftn_uns_ac_rs.messages.PostMessageRequest;
import https.ftn_uns_ac_rs.messages.TMessage;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.adminservice.dto.ChatDTO;
import xws.team16.adminservice.dto.MessageDTO;
import xws.team16.adminservice.dto.UserDTO;
import xws.team16.adminservice.model.Message;
import xws.team16.adminservice.model.User;
import xws.team16.adminservice.repository.MessageRepository;

import java.util.*;

@Service @Slf4j
public class MessageService {

    private MessageRepository messageRepository;
    private UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public ResponseEntity<?> getAll(Long userId) {
        log.info("Message service - get all messages");
        User user = this.userService.getUserById(userId);
        List<Message> messageList = this.messageRepository.findBySenderOrReceiverOrderBySent(user, user);
        Map<Long, ChatDTO> chats = new HashMap<>();

        for (Message message : messageList) {
            if (message.getSender().equals(user)) {
                attachMessage(chats, message, user, message.getReceiver(), "sent");
            } else {
                attachMessage(chats, message, user, message.getSender(), "received");
            }
        }

        return new ResponseEntity<>(chats.values(), HttpStatus.OK);
    }

    public void attachMessage(Map<Long, ChatDTO> chats, Message message, User owner, User companion, String who) {
        if (!chats.containsKey(companion.getId())) {
            UserDTO ownerDTO = new UserDTO(owner.getId(), owner.getFirstName(), owner.getLastName(), owner.getCompanyName());
            UserDTO companionDTO = new UserDTO(companion.getId(), companion.getFirstName(), companion.getLastName(), companion.getCompanyName());
            chats.put(companion.getId(), new ChatDTO(new LinkedList<>(), ownerDTO, companionDTO));
        }
        chats.get(companion.getId()).getMessages().add(
                MessageDTO.builder()
                        .id(message.getId())
                        .text(message.getText())
                        .sent(message.getSent())
                        .user(who)
                        .build());

    }

    public ResponseEntity<?> newMessage(MessageDTO messageDTO) {
        log.info("Message service - new message");
        User owner = this.userService.getUser();
        User companion = this.userService.getUserById(messageDTO.getCompanionId());

        Message message = Message.builder()
                .text(messageDTO.getText())
                .sent(messageDTO.getSent())
                .sender(messageDTO.getUser().equals("sent") ? owner : companion)
                .receiver(messageDTO.getUser().equals("sent") ? companion : owner)
                .build();

        message = this.messageRepository.save(message);
        messageDTO.setId(message.getId());

        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    public boolean newMessage(PostMessageRequest request) {
        log.info("Message service - new message");
        User owner = this.userService.getUser();
        TMessage tMessage = request.getMessage();
        User companion = this.userService.getUserById(tMessage.getCompanionId());

        Message message = Message.builder()
                .text(tMessage.getText())
                .sent(new DateTime(tMessage.getDateSent()))
                .sender(tMessage.getUser().equals("sent") ? owner : companion)
                .receiver(tMessage.getUser().equals("sent") ? companion : owner)
                .build();

        this.messageRepository.save(message);

        log.info("Message successfully saved");
        return true;
    }
}

package xws.team16.adminservice.service;

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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public ResponseEntity<?> getAll(Long userId) {
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
            chats.put(companion.getId(), new ChatDTO(new HashSet<>(), ownerDTO, companionDTO));
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
        User owner = this.userService.getUser();
        User companion = this.userService.getUserById(messageDTO.getCompanionId());

        Message message = Message.builder()
                .text(messageDTO.getText())
                .sent(messageDTO.getSent())
                .sender(messageDTO.getUser().equals("sent") ? owner : companion)
                .receiver(messageDTO.getUser().equals("sent") ? companion : owner)
                .build();

        message = this.messageRepository.save(message);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

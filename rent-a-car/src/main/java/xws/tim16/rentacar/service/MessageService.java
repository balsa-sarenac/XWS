package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.client.AdminClient;
import xws.tim16.rentacar.dto.ChatDTO;
import xws.tim16.rentacar.dto.MessageDTO;
import xws.tim16.rentacar.dto.UserDTO;
import xws.tim16.rentacar.model.Message;
import xws.tim16.rentacar.model.User;
import xws.tim16.rentacar.repository.MessageRepository;
import xws.tim16.rentacar.generated.TMessage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service @Slf4j
public class MessageService {

    private MessageRepository messageRepository;
    private UserService userService;
    private AdminClient adminClient;


    @Autowired
    public MessageService(MessageRepository messageRepository, UserService userService, AdminClient adminClient) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.adminClient = adminClient;
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

        log.info("Sending soap request to create message");
        this.adminClient.postMessage(createTMessageFromDTO(messageDTO));

        log.info("Soap request successful");
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    private TMessage createTMessageFromDTO(MessageDTO messageDTO) {
        TMessage tMessage = new TMessage();
//        tMessage.setId(messageDTO.getId());
        tMessage.setCompanionId(messageDTO.getCompanionId());
        tMessage.setDateSent(messageDTO.getSent().getMillis());
        tMessage.setText(messageDTO.getText());
        tMessage.setUser(messageDTO.getUser());
        return tMessage;
    }

}

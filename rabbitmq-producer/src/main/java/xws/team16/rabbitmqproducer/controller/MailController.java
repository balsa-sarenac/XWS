package xws.team16.rabbitmqproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.rabbitmqproducer.component.MailProducer;
import xws.team16.rabbitmqproducer.dto.MailDTO;

@RestController
public class MailController {

    private Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private MailProducer mailProducer;

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<?> sendMail(@RequestBody MailDTO mailDTO) throws JsonProcessingException {
        logger.info("Sending mail");
        this.mailProducer.sendTo("mail-queue", mailDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

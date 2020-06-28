package xws.team16.rabbitmqproducer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.rabbitmqproducer.component.MailProducer;

@RestController
public class MailController {

    private Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private MailProducer mailProducer;

    @PostMapping(value = "/mail")
    public ResponseEntity<?> sendMail() {
        logger.info("Sending mail");
        this.mailProducer.sendTo("mail-queue", "hey");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

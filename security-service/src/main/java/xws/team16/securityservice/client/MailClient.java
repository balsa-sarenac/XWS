package xws.team16.securityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xws.team16.securityservice.dto.MailDTO;

@FeignClient(name = "rabbitmq")
public interface MailClient {

    @PostMapping(value = "/", consumes = "application/json")
    ResponseEntity<?> sendMail(@RequestBody MailDTO mailDTO);

}

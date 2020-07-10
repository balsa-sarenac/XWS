package xws.team16.securityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xws.team16.securityservice.dto.UserDTO;

@FeignClient(name = "request")
public interface RequestClient {

    @GetMapping(value = "/bill/check/{id}")
    boolean checkPaid(@PathVariable Long id);

    @PostMapping(value = "/user")
    Void addUser(@RequestBody UserDTO userDTO);
}

package xws.team16.securityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xws.team16.securityservice.dto.UserDTO;

@FeignClient(name = "car")
public interface CarClient {

    @PostMapping(value = "/user")
    Void addUser(@RequestBody UserDTO userDTO);

    @GetMapping(value = "/ad/removeAll/{user_id}")
    Void removeAds(@PathVariable Long user_id);
}

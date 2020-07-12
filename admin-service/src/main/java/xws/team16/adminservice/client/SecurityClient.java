package xws.team16.adminservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xws.team16.adminservice.dto.UserByUsernameDTO;

@FeignClient(name = "security")
public interface SecurityClient {

    @GetMapping(value = "/{username}")
    ResponseEntity<UserByUsernameDTO> getUser(@PathVariable String username);
}

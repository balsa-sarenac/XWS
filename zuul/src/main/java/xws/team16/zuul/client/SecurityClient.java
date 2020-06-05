package xws.team16.zuul.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import xws.team16.zuul.dto.RoleDTO;
import xws.team16.zuul.dto.TokenDTO;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "security")
public interface SecurityClient {

    @GetMapping(name = "/verify")
    RoleDTO verify(@RequestBody TokenDTO token);
}

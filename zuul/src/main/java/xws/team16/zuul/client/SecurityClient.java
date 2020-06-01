package xws.team16.zuul.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "security")
public class SecurityClient {

//    @GetMapping(name = "/verify/{username}")
}

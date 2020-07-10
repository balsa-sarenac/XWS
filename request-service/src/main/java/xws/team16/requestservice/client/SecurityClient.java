package xws.team16.requestservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "security")
public interface SecurityClient {

    @GetMapping(value = "/rentPrivilege/{privilege}/{id}")
    Void rentPrivileges(@PathVariable Boolean privilege, @PathVariable Long id);
}

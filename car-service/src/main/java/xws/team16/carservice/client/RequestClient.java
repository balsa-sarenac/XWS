package xws.team16.carservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xws.team16.carservice.dto.OccupiedDTO;

@FeignClient(name = "request")
public interface RequestClient {

    @PostMapping(value = "/occupied", consumes = "application/json")
    Void occupiedRequests(@RequestBody OccupiedDTO occupiedDTO);
}

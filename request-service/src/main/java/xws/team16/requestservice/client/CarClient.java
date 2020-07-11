package xws.team16.requestservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xws.team16.requestservice.dto.OccupiedDTO;

@FeignClient(name = "car")
public interface CarClient {

    @PostMapping("/occupied/rent")
    ResponseEntity<?> addNewRequestOccupation(@RequestBody OccupiedDTO occupiedDTO);

}

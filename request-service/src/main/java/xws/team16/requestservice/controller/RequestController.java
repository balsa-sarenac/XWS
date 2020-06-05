package xws.team16.requestservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.requestservice.dto.OccupiedDTO;
import xws.team16.requestservice.dto.ShoppingCartDTO;
import xws.team16.requestservice.service.RentRequestService;

@RestController @Slf4j
public class RequestController {

    @Autowired
    private RentRequestService rentRequestService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> newRequests(@RequestBody ShoppingCartDTO shoppingCart) {
        log.info("Request controller - new requests");
        return this.rentRequestService.newRequests(shoppingCart);
    }

    @PostMapping(value = "/occupied" , consumes = "application/json")
    public ResponseEntity<?> occupiedRequests(@RequestBody OccupiedDTO occupiedDTO) {
        log.info("Request controller - occupied requests");
        return this.rentRequestService.cancelOccupiedRequests(occupiedDTO);
    }
}

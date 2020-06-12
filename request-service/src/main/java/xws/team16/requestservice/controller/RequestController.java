package xws.team16.requestservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.requestservice.dto.OccupiedDTO;
import xws.team16.requestservice.dto.ShoppingCartDTO;
import xws.team16.requestservice.service.RentRequestService;

@RestController @Slf4j
public class RequestController {

    @Autowired
    private RentRequestService rentRequestService;

    /**
     * Returns all requests of a user
     * @param userId id of a user !!!!!!!!!!!!!!!will be replaced!!!!!!!!!!!
     * @return List of requests
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getAll(@PathVariable Long userId) {
        log.info("Request controller - get all requests for user " + userId);
        return this.rentRequestService.getAll(userId);
    }

    /**
     * Create renting requests for a user
     * @param shoppingCart requests and bundles dto
     * @return 201 if created successfully
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> newRequests(@RequestBody ShoppingCartDTO shoppingCart) {
        log.info("Request controller - new requests");
        return this.rentRequestService.newRequests(shoppingCart);
    }

    /**
     * Cancels a request given its id
     * @param requestId id of the request
     * @return 200 ok if cancelled successfully
     */
    @PutMapping(value = "/cancel/{requestId}")
    public ResponseEntity<?> cancelRequest(@PathVariable Long requestId) {
        log.info("Request controller - cancel request with id " + requestId);
        return this.rentRequestService.cancelRequest(requestId);
    }

    @PostMapping(value = "/occupied" , consumes = "application/json")
    public ResponseEntity<?> occupiedRequests(@RequestBody OccupiedDTO occupiedDTO) {
        log.info("Request controller - occupied requests");
        return this.rentRequestService.cancelOccupiedRequests(occupiedDTO);
    }
}

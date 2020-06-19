package xws.team16.requestservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.requestservice.dto.PatchRequestDTO;
import xws.team16.requestservice.dto.PatchRequestDTO;
import xws.team16.requestservice.service.RentBundleService;

@CrossOrigin
@RestController @Slf4j
@RequestMapping(value = "/bundle")
public class BundleController {

    private RentBundleService rentBundleService;

    @Autowired
    public BundleController(RentBundleService rentBundleService) {
        this.rentBundleService = rentBundleService;
    }

    /**
     * Cancels a bundle of requests
     * @param bundle bundle to be updated
     * @return 200 ok if cancelled successfully
     */
    @PatchMapping(value = "/cancel", consumes = "application/json")
    public ResponseEntity<?> cancelBundle(@RequestBody PatchRequestDTO bundle) {
        log.info("Bundle controller - cancel bundle with id " + bundle.getId());
        return this.rentBundleService.cancelBundle(bundle.getId());
    }

    /**
     * Accepts a bundle and cancels all other requests for given cars
     * @param bundle bundle to be updated
     * @throws xws.team16.requestservice.exceptions.InvalidOperationException if bundle is not in status pending
     * @return 200 ok if everything passed successfully
     */
    @PatchMapping(value = "/accept", consumes = "application/json")
    public ResponseEntity<?> acceptBundle(@RequestBody PatchRequestDTO bundle) {
        log.info("Bundle controller - accept bundle with bundle id "+ bundle.getId());
        return this.rentBundleService.acceptBundle(bundle.getId());
    }

    /**
     * Refuses a bundle of requests
     * @param bundle bundle to be updated
     * @throws xws.team16.requestservice.exceptions.InvalidOperationException if bundle is not in status pending
     * @return 200 ok if refused successfully
     */
    @PatchMapping(value = "/refuse", consumes = "application/json")
    public ResponseEntity<?> refuseBundle(@RequestBody PatchRequestDTO bundle) {
        log.info("Bundle controller - refuse bundle with bundle id "+ bundle.getId());
        return this.rentBundleService.refuseBundle(bundle.getId());
    }
}

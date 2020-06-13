package xws.team16.requestservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.requestservice.service.RentBundleService;

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
     * @param bundleId id of the bundle
     * @return 200 ok if cancelled successfully
     */
    @PutMapping(value = "/cancel/{bundleId}")
    public ResponseEntity<?> cancelBundle(@PathVariable Long bundleId) {
        log.info("Bundle controller - cancel bundle with id " + bundleId);
        return this.rentBundleService.cancelBundle(bundleId);
    }

    /**
     * Accepts a bundle and cancels all other requests for given cars
     * @param bundleId id of the bundle
     * @throws xws.team16.requestservice.exceptions.InvalidOperationException if bundle is not in status pending
     * @return 200 ok if everything passed successfully
     */
    @PutMapping(value = "/accept/{bundleId}")
    public ResponseEntity<?> acceptBundle(@PathVariable Long bundleId) {
        log.info("Bundle controller - accept bundle with bundle id "+ bundleId);
        return this.rentBundleService.acceptBundle(bundleId);
    }
}

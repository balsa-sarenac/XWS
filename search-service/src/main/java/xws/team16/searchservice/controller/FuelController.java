package xws.team16.searchservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.searchservice.service.FuelService;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(value = "/fuel")
public class FuelController {

    private FuelService fuelService;

    @Autowired
    public FuelController(FuelService fuelService) {
        this.fuelService = fuelService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllFuels() {
        log.info("Fuel Controller - getAllFuels()");
        return fuelService.getAllFuels_ResponseEntity();
    }

}

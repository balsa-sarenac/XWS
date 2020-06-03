package xws.team16.searchservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.searchservice.service.FuelService;

@Slf4j
@RestController
@RequestMapping(value = "/fuel")
public class FuelController {

    private FuelService fuelService;

    @Autowired
    public FuelController(FuelService fuelService) {
        this.fuelService = fuelService;
    }

    @GetMapping(value="/all")
    public ResponseEntity<?> getAll() {
        log.info("Fuel controller - get fuels");
        return this.fuelService.getFuels();
    }

}

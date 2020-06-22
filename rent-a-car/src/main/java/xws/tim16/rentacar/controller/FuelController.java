package xws.tim16.rentacar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.tim16.rentacar.dto.FuelDTO;
import xws.tim16.rentacar.service.FuelService;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/fuel")
public class FuelController {

    private FuelService fuelService;

    @Autowired
    public FuelController(FuelService fuelService){
        this.fuelService = fuelService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getFuelById(@PathVariable Long id) {
        log.info("Fuel Controller - getFuelById(" + id + ")");
        return fuelService.getFuelById_ResponseEntity(id);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllFuels() {
        log.info("Fuel Controller - getAllFuels()");
        return fuelService.getAllFuels_ResponseEntity();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createOne(@RequestBody FuelDTO fuelDTO) {
        log.info("Fuel Controller - createOne(fuelDTO)");
        return fuelService.createFuel_ResponseEntity(fuelDTO);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<?> update(@RequestBody FuelDTO fuelDTO) {
        log.info("Fuel Controller - update(fuelDTO)");
        return fuelService.update_ResponseEntity(fuelDTO);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteFuelById(@PathVariable Long id) {
        log.info("Fuel Controller - deleteFuelById(" + id + ")");
        return fuelService.deleteFuelById_ResponseEntity(id);
    }
}

package xws.team16.carservice.controller;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.carservice.dto.CarDTO;
import xws.team16.carservice.service.CarService;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/car")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(value = "/statistics/{ownersID}")
    public ResponseEntity<?> getStatistics(@PathVariable Long ownersID){
        log.info("Car Controller - get statistics of owner with id " + ownersID);
        return carService.getStatistics_ResponseEntity(ownersID);
    }

    @GetMapping(value = "/{car_id}")
    public ResponseEntity<?> getCarById(@PathVariable Long car_id){
        log.info("Car controller - get car by its id (id = " + car_id + ")");
        return carService.getCarById_ResponseEntity(car_id);
    }
}

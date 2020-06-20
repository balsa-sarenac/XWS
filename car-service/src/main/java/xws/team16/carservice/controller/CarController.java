package xws.team16.carservice.controller;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.carservice.dto.CarDTO;
import xws.team16.carservice.service.CarService;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping(value = "/car")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Get car for logged user
     */
    @GetMapping(value = "/user")
    private ResponseEntity<?> getCarByUser(){
        log.info("Car controller - getting cars");
        return this.carService.getCarByUser();
    }
}

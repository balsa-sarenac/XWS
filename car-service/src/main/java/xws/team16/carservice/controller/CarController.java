package xws.team16.carservice.controller;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.carservice.dto.CarDTO;
import xws.team16.carservice.dto.CarInfoDTO;
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

    @GetMapping(value = "/user")
    private ResponseEntity<?> getCarByUser(){
        log.info("Car controller - getting cars");
        return this.carService.getCarByUser();
    }*/

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

    @GetMapping(value = "/user/{username}")
    private ResponseEntity<?> getCarByUser(@PathVariable String username){
        log.info("Car controller - getting cars");
        return this.carService.getCarByUser(username);
    }

    @PatchMapping(value = "")
    public ResponseEntity<?> editCar(@RequestBody CarInfoDTO carInfoDTO){
        log.info("Car controller - get car by its id (id = " + carInfoDTO.getId() + ")");
        return carService.editCar(carInfoDTO);
    }

}

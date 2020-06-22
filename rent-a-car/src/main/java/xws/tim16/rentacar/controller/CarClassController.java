package xws.tim16.rentacar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.tim16.rentacar.dto.CarClassDTO;
import xws.tim16.rentacar.service.CarClassService;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/carclass")
public class CarClassController {

    private CarClassService carClassService;

    @Autowired
    public CarClassController(CarClassService carClassService) {
        this.carClassService = carClassService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getCarClassById(@PathVariable Long id) {
        log.info("CarClass Controller - getCarClassById(" + id + ")");
        return carClassService.getCarClassById_ResponseEntity(id);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCarClasses() {
        log.info("CarClass Controller - getAllCarClasses()");
        return carClassService.getAllCarClasses_ResponseEntity();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createOne(@RequestBody CarClassDTO carClassDTO) {
        log.info("CarClass Controller - createOne(carClassDTO)");
        return carClassService.createCarClass_ResponseEntity(carClassDTO);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<?> update(@RequestBody CarClassDTO carClassDTO) {
        log.info("CarClass Controller - update(carClassDTO)");
        return carClassService.update_ResponseEntity(carClassDTO);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteCarClassById(@PathVariable Long id) {
        log.info("CarClass Controller - deleteCarClassById(" + id + ")");
        return carClassService.deleteCarClassById_ResponseEntity(id);
    }
}

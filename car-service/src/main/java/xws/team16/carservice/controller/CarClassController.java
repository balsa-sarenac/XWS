package xws.team16.carservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.carservice.service.CarClassService;

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
}

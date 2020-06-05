package xws.team16.searchservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.searchservice.service.CarClassService;

@RestController @Slf4j
@RequestMapping(value = "/carClass")
public class CarClassController {
    private CarClassService carClassService;

    @Autowired
    public CarClassController(CarClassService carClassService) {
        this.carClassService = carClassService;
    }

    @GetMapping(value="/all")
    public ResponseEntity<?> getAll() {
        log.info("CarClass controller - get car classes");
        return this.carClassService.getClasses();
    }
}

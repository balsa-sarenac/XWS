package xws.team16.searchservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.searchservice.service.CarClassService;

@CrossOrigin(value = "*")
@RestController @Slf4j
@RequestMapping(value = "/carclass")
public class CarClassController {
    private CarClassService carClassService;

    @Autowired
    public CarClassController(CarClassService carClassService) {
        this.carClassService = carClassService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllCarClasses() {
        log.info("CarClass Controller - getAllCarClasses()");
        return carClassService.getAllCarClasses_ResponseEntity();
    }
}

package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.CarDTO;

@Service @Slf4j
public class CarService {


    public ResponseEntity<Void> newCar(CarDTO carDTO) {
        log.info("Car service - new car");


        return new ResponseEntity<>(HttpStatus.OK);
    }
}

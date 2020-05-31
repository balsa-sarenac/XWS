package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.CarClass;
import xws.team16.carservice.repository.CarClassRepository;

@Service @Slf4j
public class CarClassService {

    private CarClassRepository carClassRepository;

    @Autowired
    public CarClassService(CarClassRepository carClassRepository) {
        this.carClassRepository = carClassRepository;
    }

    public CarClass getCarClassById(Long carClassId) {
        log.info("Car class service - getting class by id");
        return this.carClassRepository.findById(carClassId).orElseThrow(() -> new NotFoundException("Car class with given id was not found."));
    }
}

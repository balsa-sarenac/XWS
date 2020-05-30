package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.CarDTO;
import xws.team16.carservice.exceptions.*;
import xws.team16.carservice.model.*;
import xws.team16.carservice.repository.*;

import java.util.Optional;

@Service @Slf4j
public class CarService {

    private ModelRepository modelRepository;
    private MarkRepository markRepository;
    private CarRepository carRepository;
    private FuelRepository fuelRepository;
    private CarClassRepository carClassRepository;
    private GearboxRepository gearboxRepository;
    private UserRepository userRepository;

    @Autowired
    public CarService(ModelRepository modelRepository, MarkRepository markRepository, CarRepository carRepository,
                      FuelRepository fuelRepository, CarClassRepository carClassRepository,
                      GearboxRepository gearboxRepository, UserRepository userRepository) {
        this.modelRepository = modelRepository;
        this.markRepository = markRepository;
        this.carRepository = carRepository;
        this.fuelRepository = fuelRepository;
        this.carClassRepository = carClassRepository;
        this.gearboxRepository = gearboxRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Void> newCar(CarDTO carDTO) {
        log.info("Car service - new car");
        Model model = this.modelRepository.findById(carDTO.getModelId()).orElseThrow(ModelNotFoundException::new);
        Mark mark = this.markRepository.findById(carDTO.getMarkId()).orElseThrow(MarkNotFoundException::new);
        Fuel fuel = this.fuelRepository.findById(carDTO.getFuelId()).orElseThrow(FuelNotFoundException::new);
        CarClass carClass = this.carClassRepository.findById(carDTO.getCarClassId()).orElseThrow(CarClassNotFoundException::new);
        Gearbox gearbox = this.gearboxRepository.findById(carDTO.getGearboxId()).orElseThrow(GearboxNotFoundException::new);
        User user = this.userRepository.findById(carDTO.getUserId()).orElseThrow(UserNotFoundException::new);

        Car car = new Car();
        car.setModel(model);
        car.setMark(mark);
        car.setFuel(fuel);
        car.setCarClass(carClass);
        car.setGearbox(gearbox);
        car.setOwner(user);

        car = this.carRepository.save(car);
        log.info("Car added with id " + car.getId());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

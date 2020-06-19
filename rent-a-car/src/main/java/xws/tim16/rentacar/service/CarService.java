package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.dto.CarDTO;
import xws.tim16.rentacar.model.*;
import xws.tim16.rentacar.repository.CarRepository;

@Service @Slf4j
public class CarService {

    private CarRepository carRepository;
    private ModelService modelService;
    private MarkService markService;
    private FuelService fuelService;
    private CarClassService carClassService;
    private GearboxService gearboxService;
    private UserService userService;

    @Autowired
    public CarService(CarRepository carRepository, ModelService modelService, MarkService markService,
                      FuelService fuelService, CarClassService carClassService, GearboxService gearboxService,
                      UserService userService) {
        this.carRepository = carRepository;
        this.modelService = modelService;
        this.markService = markService;
        this.fuelService = fuelService;
        this.carClassService = carClassService;
        this.gearboxService = gearboxService;
        this.userService = userService;
    }

    public Car newCar(CarDTO carDTO) {
        log.info("Car service - new car");
        Model model = this.modelService.getModelById(carDTO.getModelId());
        Mark mark = this.markService.getMarkById(carDTO.getMarkId());
        Fuel fuel = this.fuelService.getFuelById(carDTO.getFuelId());
        CarClass carClass = this.carClassService.getCarClassById(carDTO.getCarClassId());;
        Gearbox gearbox = this.gearboxService.getGearboxById(carDTO.getGearboxId());
        User user = this.userService.getUserByUsername("bax");

        Car car = new Car();
        car.setModel(model);
        car.setMark(mark);
        car.setFuel(fuel);
        car.setCarClass(carClass);
        car.setGearbox(gearbox);
        car.setOwner(user);
        car.setKilometrage(carDTO.getKilometrage());
        car.setNumberOfChildSeats(carDTO.getNumberOfChildSeats());


        car = this.carRepository.save(car);
        log.info("Car added with id " + car.getId());

        return car;
    }

    public Car getCar(Long id) {
        log.info("Car service - get car");

        Car car = carRepository.getOne(id);

        log.info("Car getted with id " + car.getId());
        return car;
    }
}

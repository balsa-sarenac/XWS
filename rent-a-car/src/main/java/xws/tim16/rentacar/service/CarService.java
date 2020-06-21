package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.client.CarClient;
import xws.tim16.rentacar.dto.*;
import xws.tim16.rentacar.generated.GetStatisticsResponse;
import xws.tim16.rentacar.model.*;
import xws.tim16.rentacar.repository.CarRepository;

import java.util.List;
import java.util.Set;

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
    private CarClient carClient;

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

    public Float getAverageGrade(Car car){
        /* Returns null if car has no grades. */
        /* Returns null if car equals to null. */

        if (car == null)
            return null;
        /* Maybe it would be better to return -1, so we could distinguish this situation
         *  from one where the car is not null, but it has no grades. */

        int sum = 0;
        for (Grade g : car.getGrades()){
            sum += g.getGrade();
        }

        if (sum == 0) {
            return null;
        } else {
            Float averageGrade = new Float(0);
            averageGrade = (float) sum / car.getGrades().size();
            return averageGrade;
        }
    }

    public Car getCarWithHighestGradeByOwnersId(Set<Car> cars){
        /* Returns null if all cars have 0 grades. */

        log.info("Car service - get car with best grade");

        float maxAverageGrade = 0;
        Car carWithBHighestAverageGrade = new Car();

        for(Car c : cars){
            if (getAverageGrade(c) == null)
                continue;

            if (getAverageGrade(c) > maxAverageGrade){
                maxAverageGrade = getAverageGrade(c);
                carWithBHighestAverageGrade = c;
            }
        }

        if (maxAverageGrade == 0)
            return null;

        return carWithBHighestAverageGrade;
    }

    public Car getCarWithMostCommentsByOwnersId(Set<Car> cars){
        /* Returns null if all cars have 0 comments. */

        log.info("Car service - get car with most comments");

        int maxComments = 0;
        Car carWithMostComments = new Car();

        for(Car c : cars){
            if (c.getComments().size() > maxComments){
                maxComments = c.getComments().size();
                carWithMostComments = c;
            }
        }

        if (maxComments == 0)
            return null;

        return carWithMostComments;
    }

    public Car getCarWithMostKilometersByOwnersId(Set<Car> cars){
        /* Returns null if all cars have kilometrage equal to 0. */

        log.info("Car service - get car with most kilometers");

        double mostKilometers = 0;
        Car carWithMostKilometers = new Car();

        for (Car c : cars){
            if (c.getKilometrage() > mostKilometers){
                mostKilometers = c.getKilometrage();
                carWithMostKilometers = c;
            }
        }

        if (mostKilometers == 0)
            return null;

        return carWithMostKilometers;
    }

    public ResponseEntity<?> getStatistics_ResponseEntity(Long ownersID) {
        log.info("Car service - sending soap synchronisation request");

        Set<Car> cars = this.carRepository.findAllByOwner_Id(ownersID);
        GetStatisticsResponse response = this.carClient.getStatistics(ownersID);

        log.info("Soap request successful, synchronising database");

        synchroniseStatistics(response, cars);

        log.info("Car service - database synchronised, proceeding...");

        /* Returns ResponseEntity<StatisticsDTO>.
         *  StatisticsDTO has fields: carWithHighestGrade, carWithMostComments, carWithMostKilometers.
         *  In case of not finding any of these 3 cars, it'll just return null instead of the actual car. */

        log.info("Car service - getStatistic_ResponseEntity(" + ownersID + ")");

        StatisticsDTO statisticsDTO = new StatisticsDTO();

        Car carWithHighestAverageGrade = getCarWithHighestGradeByOwnersId(cars);
        if (carWithHighestAverageGrade != null){
            CarWithHighestGradeDTO carDTO = new CarWithHighestGradeDTO();

            carDTO.setId(carWithHighestAverageGrade.getId());
            carDTO.setMarkId(carWithHighestAverageGrade.getMark().getId());
            carDTO.setMarkName(carWithHighestAverageGrade.getMark().getName());
            carDTO.setModelId(carWithHighestAverageGrade.getModel().getId());
            carDTO.setModelName(carWithHighestAverageGrade.getModel().getName());
            carDTO.setAverageGrade(getAverageGrade(carWithHighestAverageGrade));

            statisticsDTO.setCarWithHighestGrade(carDTO);
        }

        Car carWithMostComments = getCarWithMostCommentsByOwnersId(cars);
        if (carWithMostComments != null){
            CarWithMostCommentsDTO carDTO = new CarWithMostCommentsDTO();

            carDTO.setId(carWithMostComments.getId());
            carDTO.setMarkId(carWithMostComments.getMark().getId());
            carDTO.setMarkName(carWithMostComments.getMark().getName());
            carDTO.setModelId(carWithMostComments.getModel().getId());
            carDTO.setModelName(carWithMostComments.getModel().getName());
            carDTO.setNumberOfComments(carWithMostComments.getComments().size());

            statisticsDTO.setCarWithMostComments(carDTO);
        }

        Car carWithMostKilometers = getCarWithMostKilometersByOwnersId(cars);
        if (carWithMostKilometers != null){
            CarWithMostKilometersDTO carDTO = new CarWithMostKilometersDTO();

            carDTO.setId(carWithMostKilometers.getId());
            carDTO.setMarkId(carWithMostKilometers.getMark().getId());
            carDTO.setMarkName(carWithMostKilometers.getMark().getName());
            carDTO.setModelId(carWithMostKilometers.getModel().getId());
            carDTO.setModelName(carWithMostKilometers.getModel().getName());
            carDTO.setKilometrage(carWithMostKilometers.getKilometrage());

            statisticsDTO.setCarWithMostKilometers(carDTO);
        }

        return new ResponseEntity<StatisticsDTO>(statisticsDTO, HttpStatus.FOUND);
    }

    /**
     * Evaluates data from other database and this database for selected user
     * @param response soap response with all users cars and important info
     */
    private void synchroniseStatistics(GetStatisticsResponse response, Set<Car> cars) {

    }
}

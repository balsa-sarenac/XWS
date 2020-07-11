package xws.team16.carservice.service;

import xws.team16.carservice.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.generated.ad.TCar;
import xws.team16.carservice.generated.car.*;
import xws.team16.carservice.model.*;
import xws.team16.carservice.repository.CarRepository;
import xws.team16.carservice.repository.MyImageRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
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
    private ModelMapper modelMapper;

    @Autowired
    private MyImageRepository myImageRepository;

    @Autowired
    public CarService(CarRepository carRepository, ModelService modelService, MarkService markService, FuelService fuelService,
                      CarClassService carClassService, GearboxService gearboxService, UserService userService, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelService = modelService;
        this.markService = markService;
        this.fuelService = fuelService;
        this.carClassService = carClassService;
        this.gearboxService = gearboxService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public Car newCar(CarDTO carDTO) throws SQLException {
        log.info("New car");
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
//        car.setImages(images);

        car = this.carRepository.save(car);
        log.info("Car added with id " + car.getId());

        Set<MyImage> images = new HashSet<>();
        if (carDTO.getImages() != null) {
            for (String image: carDTO.getImages()) {
                MyImage myImage = extractImage(image);
                myImage.setCar(car);
                images.add(myImage);
            }
        }
        this.myImageRepository.saveAll(images);

        return car;
    }

    public Car getCar(Long id) {
        log.info("Car service - get car");

        Car car = carRepository.findById(id).orElseThrow(() -> new NotFoundException("CAr with given id was not found"));

        log.info("Car getted with id " + car.getId());
        return car;
    }

    public Car saveCar(Car car){
       return this.carRepository.save(car);
    }

    public Car newCar(TCar carDTO) {
        log.info("Car service - new car");
        Model model = this.modelService.getModelById(carDTO.getModelId());
        Mark mark = this.markService.getMarkById(carDTO.getMarkId());
        Fuel fuel = this.fuelService.getFuelById(carDTO.getFuelId());
        CarClass carClass = this.carClassService.getCarClassById(carDTO.getCarClassId());
        ;
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

    public MyImage extractImage(String string) throws SQLException {
        String[] parts = string.split(",");
        byte[] decodedByte = Base64.decode(parts[1]);
        String[] info = parts[0].split("/");
        String type = info[1].split(";")[0];
        MyImage myImage = new MyImage();
        myImage.setImage(new SerialBlob(decodedByte));
        myImage.setInfo(parts[0]);
        myImage.setType(type);
        return myImage;
    }

    public String encodeImage(MyImage myImage) throws SQLException {
        String retVal = "data:image/jpeg;base64,";
        String img = Base64.toBase64String(myImage.getImage().getBytes(1L, (int) myImage.getImage().length()));
        return retVal + img;
    }

    public ResponseEntity<?> getCarByUser(String username) {
        User user = this.userService.getUserByUsername(username);
        List<Car> cars = this.carRepository.findAllByOwnerId(user.getId());
        List<CarInfoDTO> carInfoDTOS = new ArrayList<>();

        for (Car car : cars) {
            CarInfoDTO carInfoDTO = new CarInfoDTO();
            carInfoDTO.setId(car.getId());
            carInfoDTO.setFuel(modelMapper.map(car.getFuel(), FuelDTO.class));
            ModelInfoDTO modelDTO = new ModelInfoDTO();
            modelDTO.setId(car.getModel().getId());
            modelDTO.setName(car.getModel().getName());
            MarkInfoDTO markDTO = new MarkInfoDTO();
            markDTO.setId(car.getMark().getId());
            markDTO.setName(car.getMark().getName());
            carInfoDTO.setMark(markDTO);
            carInfoDTO.setModel(modelDTO);
            carInfoDTOS.add(carInfoDTO);
        }
        return new ResponseEntity<>(carInfoDTOS, HttpStatus.OK);
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

    public Set<Car> getAllCarsByOwnersId(Long ownersID){
        log.info("Car service - get all cars by owner's id (id: " + ownersID + ")");

        User owner = userService.getUserById(ownersID);
        //return carRepository.findAllByOwner(owner).orElseThrow(() -> new NotFoundException("Not one car was found."));
        return carRepository.getAllByOwner(owner);
        // ako ne nađe nijedna kola, da li vraća null ili prazan Set<Car> ???
    }

    public Car getCarWithHighestGradeByOwnersId(Long ownersID){
        /* Returns null if all cars have 0 grades. */

        log.info("Car service - get car with best grade by owner's id (id: " + ownersID + ")");

        Set<Car> allCars = getAllCarsByOwnersId(ownersID);

        float maxAverageGrade = 0;
        Car carWithBHighestAverageGrade = new Car();

        for(Car c : allCars){
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

    public Car getCarWithMostCommentsByOwnersId(Long ownersID){
        /* Returns null if all cars have 0 comments. */

        log.info("Car service - get car with most comments by owner's id (id: " + ownersID + ")");

        Set<Car> allCars = getAllCarsByOwnersId(ownersID);

        int maxComments = 0;
        Car carWithMostComments = new Car();

        for(Car c : allCars){
            if (c.getComments().size() > maxComments){
                maxComments = c.getComments().size();
                carWithMostComments = c;
            }
        }

        if (maxComments == 0)
            return null;

        return carWithMostComments;
    }

    public Car getCarWithMostKilometersByOwnersId(Long ownersID){
        /* Returns null if all cars have kilometrage equal to 0. */

        log.info("Car service - get car with most kilometers by owner's id (id: " + ownersID + ")");

        Set<Car> allCars = getAllCarsByOwnersId(ownersID);

        double mostKilometers = 0;
        Car carWithMostKilometers = new Car();

        for (Car c : allCars){
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
        /* Returns ResponseEntity<StatisticsDTO>.
        *  StatisticsDTO has fields: carWithHighestGrade, carWithMostComments, carWithMostKilometers.
        *  In case of not finding any of these 3 cars, it'll just return null instead of the actual car. */

        log.info("Car service - getStatistic_ResponseEntity(" + ownersID + ")");

        StatisticsDTO statisticsDTO = new StatisticsDTO();

        Car carWithHighestAverageGrade = getCarWithHighestGradeByOwnersId(ownersID);
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

        Car carWithMostComments = getCarWithMostCommentsByOwnersId(ownersID);
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

        Car carWithMostKilometers = getCarWithMostKilometersByOwnersId(ownersID);
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

        return new ResponseEntity<StatisticsDTO>(statisticsDTO, HttpStatus.OK);
    }

    public GetStatisticsResponse getUserCars(long userId) {
        GetStatisticsResponse response = new GetStatisticsResponse();
        List<Car> cars = this.carRepository.findAllByOwner_Id(userId);
        for (Car car: cars) {
            TCarStatistics statistics = new TCarStatistics();
            statistics.setId(car.getId());
            List<TComment> comments = convertToTComment(car.getComments());
            List<TReport> reports = convertToTReport(car.getReports());
            List<TGrade> grades = convertToTGrade(car.getGrades());
            statistics.getComments().addAll(comments);
            statistics.getReports().addAll(reports);
            statistics.getGrades().addAll(grades);
            response.getCars().add(statistics);
        }
        log.info("Returning cars of a user with statistics info");

        return response;
    }

    private List<TGrade> convertToTGrade(Set<Grade> grades) {
        List<TGrade> tGrades = new ArrayList<>();
        for (Grade grade: grades) {
            TGrade tGrade = new TGrade();
            tGrade.setId(grade.getId());
            tGrade.setGrade(grade.getGrade());
            tGrade.setUserId(grade.getUser().getId());
            tGrades.add(tGrade);
        }
        return tGrades;
    }

    private List<TReport> convertToTReport(Set<Report> reports) {
        List<TReport> tReports = new ArrayList<>();
        for (Report report: reports) {
            TReport tReport = new TReport();
            tReport.setId(report.getId());
            tReport.setComment(report.getComment());
            tReport.setKilometrage(report.getKilometrage());
            tReports.add(tReport);
        }
        return tReports;
    }

    private List<TComment> convertToTComment(Set<Comment> comments) {
        List<TComment> tComments = new ArrayList<>();
        for (Comment comment : comments) {
            TComment tComment = new TComment();
            tComment.setId(comment.getId());
            tComment.setText(comment.getText());
            tComment.setApproved(comment.isApproved());
            tComment.setUserId(comment.getUser().getId());
            tComments.add(tComment);
        }
        return tComments;
    }

//    public Car updateCarsKilometrage(Car car, double newKilometers){
//        log.info("Car service - updating car's kilometers");
//
//        car.setKilometrage(car.getKilometrage() + newKilometers);
//        // carRepository.save(car); // This line is unnecessary. It works without it.
//
//        return car;
//    }

    public ResponseEntity<?> getCarById_ResponseEntity(Long car_id) {
        log.info("Car service - getCarById_ResponseEntity(" + car_id + ")");

        Car car = getCar(car_id);

        if (car == null)
            return new ResponseEntity<>("Car with id " + car_id + " does not exist", HttpStatus.NOT_FOUND);

        CarInfoDTO carDTO = new CarInfoDTO();

        carDTO.setId(car.getId());

        ModelInfoDTO modelDTO = new ModelInfoDTO();
        modelDTO.setId(car.getModel().getId());
        modelDTO.setName(car.getModel().getName());
        carDTO.setModel(modelDTO);

        MarkInfoDTO markDTO = new MarkInfoDTO();
        markDTO.setId(car.getMark().getId());
        markDTO.setName(car.getMark().getName());
        carDTO.setMark(markDTO);

        CarClassDTO carClassDTO = new CarClassDTO();
        carClassDTO.setId(car.getCarClass().getId());
        carClassDTO.setName(car.getCarClass().getName());
        carDTO.setCarClass(carClassDTO);

        FuelDTO fuelDTO = new FuelDTO();
        fuelDTO.setId(car.getFuel().getId());
        fuelDTO.setType(car.getFuel().getType());
        carDTO.setFuel(fuelDTO);

        GearboxDTO gearboxDTO = new GearboxDTO();
        gearboxDTO.setId(car.getGearbox().getId());
        gearboxDTO.setType(car.getGearbox().getType());
        carDTO.setGearbox(gearboxDTO);

        carDTO.setKilometrage((int)car.getKilometrage());
        carDTO.setNumberOfChildSeats(car.getNumberOfChildSeats());
        carDTO.setHasAndroid(car.isHasAndroid());
        carDTO.setOverallGrade(getOverallGrade(car));
        carDTO.setNumberGrades(car.getGrades().size());

        return new ResponseEntity<CarInfoDTO>(carDTO, HttpStatus.OK);
    }

    public Car updateCarsKilometrage(Car car, double newKilometers){
        log.info("Car service - updating car's kilometers");

        car.setKilometrage(car.getKilometrage() + newKilometers);
        // carRepository.save(car); // This line is unnecessary. It works without it.

        return car;
    }

    public List<Car> getCarByUserUsername(String username) {
        User user = this.userService.getUserByUsername(username);
        List<Car> cars = this.carRepository.findAllByOwnerId(user.getId());
        return cars;
    }

    public float getOverallGrade(Car car) {
        log.info("Car service - getOverallGrade(car)");

        float sum = 0;
        for (Grade grade : car.getGrades()) {
            sum += grade.getGrade();
        }

        return sum;
    }
}

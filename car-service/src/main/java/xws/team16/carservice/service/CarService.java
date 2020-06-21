package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.*;
import xws.team16.carservice.generated.ad.TCar;
import xws.team16.carservice.generated.car.*;
import xws.team16.carservice.model.*;
import xws.team16.carservice.repository.CarRepository;

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

    public Car newCar(CarDTO carDTO) throws SQLException {
        log.info("Car service - new car");
        Model model = this.modelService.getModelById(carDTO.getModelId());
        Mark mark = this.markService.getMarkById(carDTO.getMarkId());
        Fuel fuel = this.fuelService.getFuelById(carDTO.getFuelId());
        CarClass carClass = this.carClassService.getCarClassById(carDTO.getCarClassId());;
        Gearbox gearbox = this.gearboxService.getGearboxById(carDTO.getGearboxId());
        User user = this.userService.getUserByUsername("bax");

//        Set<MyImage> images = new HashSet<>();
//        for (ImageDTO image: carDTO.getImages()) {
//            MyImage myImage = new MyImage();
//            myImage.setName(image.getName());
//            myImage.setType(image.getType());
//            myImage.setImage(image.getImage());
//            images.add(myImage);
//        }
        Set<MyImage> images = new HashSet<>();
        for (String image: carDTO.getImages()) {
            MyImage myImage = extractImage(image);
            images.add(myImage);
        }

        Car car = new Car();
        car.setModel(model);
        car.setMark(mark);
        car.setFuel(fuel);
        car.setCarClass(carClass);
        car.setGearbox(gearbox);
        car.setOwner(user);
        car.setKilometrage(carDTO.getKilometrage());
        car.setNumberOfChildSeats(carDTO.getNumberOfChildSeats());
        car.setImages(images);

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

        return new ResponseEntity<StatisticsDTO>(statisticsDTO, HttpStatus.FOUND);
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
        for (Comment comment: comments) {
            TComment tComment = new TComment();
            tComment.setId(comment.getId());
            tComment.setText(comment.getText());
            tComment.setApproved(comment.isApproved());
            tComment.setUserId(comment.getUser().getId());
            tComments.add(tComment);
        }
        return tComments;
    }
}

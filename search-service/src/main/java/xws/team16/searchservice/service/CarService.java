package xws.team16.searchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.searchservice.dto.CarDTO;
import xws.team16.searchservice.dto.NewCarRequestDTO;
import xws.team16.searchservice.model.*;
import xws.team16.searchservice.repository.CarRepository;
import xws.team16.searchservice.repository.MyImageRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service @Slf4j
public class CarService {

    private ModelService modelService;
    private MarkService markService;
    private FuelService fuelService;
    private CarClassService carClassService;
    private GearboxService gearboxService;
    private CarRepository carRepository;
    private MyImageRepository myImageRepository;

    @Autowired
    public CarService(ModelService modelService, MarkService markService, FuelService fuelService,
                      CarClassService carClassService, GearboxService gearboxService, CarRepository carRepository,
                      MyImageRepository myImageRepository) {
        this.modelService = modelService;
        this.markService = markService;
        this.fuelService = fuelService;
        this.carClassService = carClassService;
        this.gearboxService = gearboxService;
        this.carRepository = carRepository;
        this.myImageRepository = myImageRepository;
    }

    public Car newCar(NewCarRequestDTO carDTO) throws SQLException {
        log.info("New car");
        Model model = this.modelService.getModelById(carDTO.getModelId());
        Mark mark = this.markService.getMarkById(carDTO.getMarkId());
        Fuel fuel = this.fuelService.getFuelById(carDTO.getFuelId());
        CarClass carClass = this.carClassService.getCarClassById(carDTO.getCarClassId());;
        Gearbox gearbox = this.gearboxService.getGearboxById(carDTO.getGearboxId());
//        User user = this.userService.getUserByUsername("bax");

        Car car = new Car();
        car.setModel(model);
        car.setMark(mark);
        car.setFuel(fuel);
        car.setCarClass(carClass);
        car.setGearbox(gearbox);
//        car.setOwner(user);
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

    /**
     * Transforms all MyImages from Ad to base 64 strings
     * @param ad whose images you wish to transform
     * @return list of 64 encoded images
     * @throws SQLException
     */
    public List<String> transformImages(Ad ad) throws SQLException {
        List<String> retVal = new ArrayList<>();
        for (MyImage myImage: ad.getCar().getImages())
            retVal.add(encodeImage(myImage));
        return retVal;
    }
}

package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.CarClassDTO;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.Car;
import xws.team16.carservice.model.CarClass;
import xws.team16.carservice.repository.CarClassRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class CarClassService {

    private CarClassRepository carClassRepository;

    @Autowired
    public CarClassService(CarClassRepository carClassRepository) {
        this.carClassRepository = carClassRepository;
    }

    public CarClass getCarClassById(Long id) {
        log.info("Car class service - getting class by id");
        return this.carClassRepository.findById(id).orElseThrow(() -> new NotFoundException("Car class with given id was not found."));
    }

    public ResponseEntity<?> getCarClassById_ResponseEntity(Long id) {
        log.info("CarClass Service - getCarClassById_ResponseEntity(" + id + ")");

        CarClass carClass = getCarClassById(id);

        CarClassDTO carClassDTO = new CarClassDTO();
        carClassDTO.setId(carClass.getId());
        carClassDTO.setName(carClass.getName());

        return new ResponseEntity<CarClassDTO>(carClassDTO, HttpStatus.FOUND);
    }

    public List<CarClass> getAllCarClasses() {
        log.info("CarClass Service - getAllCarClasses()");
        return carClassRepository.findAll();
    }

    public ResponseEntity<?> getAllCarClasses_ResponseEntity() {
        log.info("CarClass Service - getAllCarClasses_ResponseEntity");

        List<CarClass> carClasses = getAllCarClasses();

        List<CarClassDTO> carClassDTOS = new ArrayList<>();
        for (CarClass cc : carClasses) {
            CarClassDTO carClassDTO = new CarClassDTO();

            carClassDTO.setId(cc.getId());
            carClassDTO.setName(cc.getName());

            carClassDTOS.add(carClassDTO);
        }

        return new ResponseEntity<List<CarClassDTO>>(carClassDTOS, HttpStatus.OK);
    }

    public CarClass createCarClass(CarClassDTO carClassDTO) {
        log.info("CarClass Service - createCarClass(carClassDTO)");

        if (carClassRepository.getByName(carClassDTO.getName()) == null) {
            CarClass carClass = new CarClass();
            carClass.setName(carClassDTO.getName());

            carClassRepository.save(carClass);

            return carClass;
        }

        return null;
    }

    public ResponseEntity<?> createCarClass_ResponseEntity(CarClassDTO carClassDTO) {
        log.info("CarClass Service - createCarClass_ResponseEntity(carClassDTO)");

        CarClass carClass = createCarClass(carClassDTO);

        if (carClass == null) {
            return new ResponseEntity<>("Car class with that name already exists.", HttpStatus.CONFLICT);
        }

        carClassDTO.setId(carClass.getId());
        return new ResponseEntity<CarClassDTO>(carClassDTO, HttpStatus.OK);
    }

    public CarClass update(CarClassDTO carClassDTO) {
        log.info("CarClass Service - update(carClassDTO)");

        CarClass carClass = carClassRepository.getById(carClassDTO.getId());

        if (carClass == null)
            return null;

        carClass.setName(carClassDTO.getName());

        carClassRepository.save(carClass);

        return carClass;
    }

    public ResponseEntity<?> update_ResponseEntity(CarClassDTO carClassDTO) {
        log.info("CarClass Service - update_ResponseEntity(carClassDTO)");

        CarClass carClass = update(carClassDTO);

        if (carClass == null)
            return new ResponseEntity<>("Car class with given id was not found", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<CarClassDTO>(carClassDTO, HttpStatus.OK);
    }

    public Long deleteCarClassById(Long id) {
        log.info("CarClass Service - deleteCarClassById(" + id + ")");

        CarClass carClass = carClassRepository.getById(id);

        if (carClass == null)
            return null;

        carClassRepository.delete(carClass);

        return id;
    }

    public ResponseEntity<?> deleteCarClassById_ResponseEntity(Long id) {
        log.info("CarClass Service - deleteCarClassById_ResponseEntity(" + id + ")");

        Long returnId = deleteCarClassById(id);

        if (returnId == id)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>("Car class with given id was not found", HttpStatus.BAD_REQUEST);
    }
}

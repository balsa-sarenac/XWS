package xws.team16.searchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.searchservice.dto.CarClassDTO;
import xws.team16.searchservice.model.CarClass;
import xws.team16.searchservice.repository.CarClassRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class CarClassService {
    private CarClassRepository carClassRepository;

    @Autowired
    public CarClassService(CarClassRepository carClassRepository) {
        this.carClassRepository = carClassRepository;
    }

    public ResponseEntity<List<CarClassDTO>> getClasses() {
        log.info("CarClass service - get all classes");
        List<CarClass> carClasses = this.carClassRepository.findAll();

        List<CarClassDTO> carClassDTOS = new ArrayList<>();
        for (CarClass carClass  : carClasses ){
            CarClassDTO carClassDTO = new CarClassDTO();
            carClassDTO.setId(carClass.getId());
            carClassDTO.setName(carClass.getName());

            carClassDTOS.add(carClassDTO);
        }

        return new ResponseEntity<>(carClassDTOS, HttpStatus.OK);
    }
}

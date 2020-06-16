package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.GradeDTO;
import xws.team16.carservice.model.Car;
import xws.team16.carservice.model.Grade;
import xws.team16.carservice.model.User;
import xws.team16.carservice.repository.GradeRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class GradeService {
    private GradeRepository gradeRepository;
    private UserService userService;
    private CarService carService;

    @Autowired
    public GradeService(GradeRepository gradeRepository, UserService userService, CarService carService) {
        this.gradeRepository = gradeRepository;
        this.userService = userService;
        this.carService = carService;
    }


    public ResponseEntity<?> createGrade(GradeDTO gradeDTO) {
        log.info("Grade service - creating grade for car with id " + gradeDTO.getCarId());
        Grade grade  = Grade.builder()
                .grade(gradeDTO.getGrade())
                .build();
        User user = this.userService.getUserByUsername(gradeDTO.getUserUsername());
        Car car = this.carService.getCar(gradeDTO.getId());
        car.setNumberOfGrades(car.getNumberOfGrades()+1);
        grade.setCar(car);
        grade.setUser(user);

        log.info("Grade service - saving grade");
        this.carService.saveCar(car);
        this.gradeRepository.save(grade);
        log.info("Grade service - grade saved");
        return  new ResponseEntity<>(HttpStatus.CREATED);

    }

    public ResponseEntity<?> getGrades(Long carId) {
        log.info("Grade service - getting grades");
        List<Grade> grades = this.gradeRepository.findByCarId(carId);
        List<GradeDTO> gradeDTOS = new ArrayList<>();

        log.info("Grade service - creating grades dto");
        for (Grade grade: grades){
            GradeDTO gradeDTO = GradeDTO.builder()
                    .id(grade.getId())
                    .grade(grade.getGrade())
                    .carId(grade.getCar().getId())
                    .userUsername(grade.getUser().getUsername())
                    .build();
            gradeDTOS.add(gradeDTO);
        }
        log.info("Grade service - returning grades");
        return new ResponseEntity<>(gradeDTOS, HttpStatus.OK);
    }
}

package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.client.CarClient;
import xws.tim16.rentacar.dto.GradeDTO;
import xws.tim16.rentacar.generated.GetGradeResponse;
import xws.tim16.rentacar.generated.TGrade;
import xws.tim16.rentacar.model.Ad;
import xws.tim16.rentacar.model.Car;
import xws.tim16.rentacar.model.Grade;
import xws.tim16.rentacar.model.User;
import xws.tim16.rentacar.repository.AdRepository;
import xws.tim16.rentacar.repository.GradeRepository;


import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class GradeService {
    private GradeRepository gradeRepository;
    private UserService userService;
    private CarService carService;
    private AdService adService;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private CarClient carClient;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public GradeService(GradeRepository gradeRepository, UserService userService, CarService carService, AdService adService) {
        this.gradeRepository = gradeRepository;
        this.userService = userService;
        this.carService = carService;
        this.adService = adService;
    }

    public ResponseEntity<?> createGrade(GradeDTO gradeDTO) {
        log.info("Grade service - creating grade for car with id " + gradeDTO.getCarId());
        Grade grade  = Grade.builder()
                .grade(gradeDTO.getGrade())
                .build();

        User user = this.userService.getUserByUsername(gradeDTO.getUserUsername());
        Ad ad  = this.adService.getAd(gradeDTO.getAdId());
        Car car = ad.getCar();
        car.setNumberOfGrades(car.getNumberOfGrades()+1);
        int sum = 0;
        for(Grade g: car.getGrades()){
            sum = sum + g.getGrade();
        }
        car.setOverallGrade(sum/car.getNumberOfGrades());
        grade.setCar(car);
        grade.setAd(ad);
        grade.setUser(user);

        Grade gradeCheck = this.gradeRepository.findByUserIdAndAdId(user.getId(),ad.getId());

        if(gradeCheck != null){
            log.info("Grade service - grade already added");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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
                    .adId(grade.getAd().getId())
                    .build();
            gradeDTOS.add(gradeDTO);
        }

        List<GradeDTO> gradesDTOS = new ArrayList<>();
        List<Ad> ads = this.adRepository.findAllByCarId(carId);
        for(Ad a: ads){
            if(a.getRefId() != null) {
                log.info("Sending soap request to car service");
                try {
                    GetGradeResponse response = this.carClient.getGrades(a.getRefId());
                    for (TGrade t : response.getGrades()) {
                        gradesDTOS.add(modelMapper.map(t, GradeDTO.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("Soap request successfully finished");
            }
        }

        for(GradeDTO gr: gradesDTOS){
            if(!gradeDTOS.contains(gr)){
                gradeDTOS.add(gr);
            }
        }

        log.info("Grade service - grades successfully retrieved");
        return new ResponseEntity<>(gradeDTOS, HttpStatus.OK);
    }

    public ResponseEntity<?> check(String username, Long id) {
        log.info("Comment service - checking comment already created");
        User user = this.userService.getUserByUsername(username);
        Grade gradeCheck = this.gradeRepository.findByUserIdAndAdId(user.getId(), id);

        if (gradeCheck != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }
}

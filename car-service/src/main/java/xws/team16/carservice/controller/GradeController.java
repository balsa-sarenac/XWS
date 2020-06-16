package xws.team16.carservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.carservice.dto.GradeDTO;
import xws.team16.carservice.service.GradeService;

@RestController @Slf4j
@RequestMapping(value = "/grade")
public class GradeController {
    private GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping()
    private ResponseEntity<?> createGrade(@RequestBody GradeDTO gradeDTO){
        log.info("Grade controller - creating grade");
        return this.gradeService.createGrade(gradeDTO);
    }

    @GetMapping(value = "/{id}")
    private ResponseEntity<?> getGradesForCar(@PathVariable Long carId){
        log.info("Grade controller - getting all grade");
        return this.gradeService.getGrades(carId);
    }
}

package xws.team16.carservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.carservice.dto.OccupiedDTO;
import xws.team16.carservice.service.OccupiedService;

@CrossOrigin(origins = "*")
@RestController @Slf4j
@RequestMapping(value = "/occupied")
public class OccupiedController {
    private OccupiedService occupiedService;

    @Autowired
    public OccupiedController(OccupiedService occupiedService) {
        this.occupiedService = occupiedService;
    }

    /**
     * Creates Occupied for specific car
     */
    @PostMapping
    public ResponseEntity<?> addNew(@RequestBody OccupiedDTO occupiedDTO) {
        log.info("Occupied controller - new occupied");
        return this.occupiedService.newOccupied(occupiedDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> addNew(@PathVariable Long id ) {
        log.info("Occupied controller - get occupied");
        return this.occupiedService.getOccupied(id);
    }

}

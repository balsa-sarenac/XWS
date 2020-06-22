package xws.tim16.rentacar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.tim16.rentacar.dto.GearboxDTO;
import xws.tim16.rentacar.service.GearboxService;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/gearbox")
public class GearboxController {

    private GearboxService gearboxService;

    @Autowired
    public GearboxController(GearboxService gearboxService) {
        this.gearboxService = gearboxService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getGearboxById(@PathVariable Long id) {
        log.info("Gearbox Controller - geGearboxById(" + id + ")");
        return gearboxService.getGearboxById_ResponseEntity(id);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllGearboxes() {
        log.info("Gearbox Controller - getAllGearboxes()");
        return gearboxService.getAllGearboxes_ResponseEntity();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createOne(@RequestBody GearboxDTO gearboxDTO) {
        log.info("Gearbox Controller - createOne(gearboxDTO)");
        return gearboxService.createGearbox_ResponseEntity(gearboxDTO);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<?> update(@RequestBody GearboxDTO gearboxDTO) {
        log.info("Gearbox Controller - update(gearboxDTO)");
        return gearboxService.update_ResponseEntity(gearboxDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteGearboxById(@PathVariable Long id) {
        log.info("Gearbox Controller - deleteGearboxById(" + id + ")");
        return gearboxService.deleteGearboxById_ResponseEntity(id);
    }
}

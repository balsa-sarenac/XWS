package xws.team16.carservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.carservice.model.Gearbox;
import xws.team16.carservice.service.GearboxService;

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
}

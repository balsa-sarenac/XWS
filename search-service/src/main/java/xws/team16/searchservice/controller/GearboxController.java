package xws.team16.searchservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.searchservice.service.GearboxService;

@RestController @Slf4j
@RequestMapping(value = "/gearbox")
public class GearboxController {
    private GearboxService gearboxService;

    @Autowired
    public GearboxController(GearboxService gearboxService) {
        this.gearboxService = gearboxService;
    }

    @GetMapping(value="/all")
    public ResponseEntity<?> getAll() {
        log.info("Gearbox controller - get gearboxes");
        return this.gearboxService.getGearboxes();
    }
}

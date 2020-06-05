package xws.team16.carservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
}

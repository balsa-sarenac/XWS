package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.Gearbox;
import xws.team16.carservice.repository.GearboxRepository;

@Service @Slf4j
public class GearboxService {

    private GearboxRepository gearboxRepository;

    @Autowired
    public GearboxService(GearboxRepository gearboxRepository) {
        this.gearboxRepository = gearboxRepository;
    }

    public Gearbox getGearboxById(Long gearboxId) {
        log.info("Gearbox service - getting gearbox by id");
        return this.gearboxRepository.findById(gearboxId).orElseThrow(() -> new NotFoundException("Gearbox with given id was nto found"));
    }
}

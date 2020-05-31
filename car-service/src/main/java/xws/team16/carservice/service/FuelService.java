package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.Fuel;
import xws.team16.carservice.repository.FuelRepository;

@Service @Slf4j
public class FuelService {

    private FuelRepository fuelRepository;

    @Autowired
    public FuelService(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    public Fuel getFuelById(Long fuelId) {
        log.info("Fuel service - getting fuel by id");
        return this.fuelRepository.findById(fuelId).orElseThrow(() -> new NotFoundException("Fuel with given id was not found."));
    }
}

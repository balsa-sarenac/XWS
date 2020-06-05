package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.FuelDTO;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.Fuel;
import xws.team16.carservice.repository.FuelRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class FuelService {

    private FuelRepository fuelRepository;

    @Autowired
    public FuelService(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    public Fuel getFuelById(Long id) {
        log.info("Fuel Service - getFuelById(" + id + ")");
        return this.fuelRepository.findById(id).orElseThrow(() -> new NotFoundException("Fuel with given id was not found."));
    }

    public ResponseEntity<?> getFuelById_ResponseEntity(Long id) {
        log.info("Fuel Service - getFuelById_ResponseEntity(" + id + ")");

        Fuel fuel = getFuelById(id);

        FuelDTO fuelDTO = new FuelDTO();
        fuelDTO.setId(fuel.getId());
        fuelDTO.setType(fuel.getType());

        return new ResponseEntity<FuelDTO>(fuelDTO, HttpStatus.FOUND);
    }

    public List<Fuel> getAllFuels() {
        log.info("Fuel Service - getAllFuels()");
        return fuelRepository.findAll();
    }

    public ResponseEntity<?> getAllFuels_ResponseEntity() {
        log.info("Fuel Service - getAllFuels_ResponseEntity");

        List<Fuel> fuels = getAllFuels();

        List<FuelDTO> fuelDTOS = new ArrayList<>();
        for (Fuel f : fuels) {
            FuelDTO fuelDTO = new FuelDTO();

            fuelDTO.setId(f.getId());
            fuelDTO.setType(f.getType());

            fuelDTOS.add(fuelDTO);
        }

        return new ResponseEntity<List<FuelDTO>>(fuelDTOS, HttpStatus.OK);
    }
}

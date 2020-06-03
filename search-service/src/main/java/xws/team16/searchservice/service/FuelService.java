package xws.team16.searchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.searchservice.dto.FuelDTO;
import xws.team16.searchservice.model.Fuel;
import xws.team16.searchservice.repository.FuelRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class FuelService {

    private FuelRepository fuelRepository;

    @Autowired
    public FuelService(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    public ResponseEntity<List<FuelDTO>> getFuels() {
        log.info("Fuel service - get all fuels");
        List<Fuel> fuels = this.fuelRepository.findAll();

        List<FuelDTO> fuelDTOS = new ArrayList<>();
        for (Fuel fuel : fuels ){
            FuelDTO fuelDTO = new FuelDTO();
            fuelDTO.setId(fuel.getId());
            fuelDTO.setType(fuel.getType());

            fuelDTOS.add(fuelDTO);
        }

        return new ResponseEntity<>(fuelDTOS,HttpStatus.OK);
    }
}

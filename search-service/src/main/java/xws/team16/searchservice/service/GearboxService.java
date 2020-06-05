package xws.team16.searchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.searchservice.dto.GearboxDTO;
import xws.team16.searchservice.model.Gearbox;
import xws.team16.searchservice.repository.GearboxRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class GearboxService {
    private GearboxRepository gearboxRepository;

    @Autowired
    public GearboxService(GearboxRepository gearboxRepository) {
        this.gearboxRepository = gearboxRepository;
    }

    public ResponseEntity<List<GearboxDTO>> getGearboxes() {
        log.info("Gearbox service - get all gearboxes");
        List<Gearbox> gearboxes = this.gearboxRepository.findAll();

        List<GearboxDTO> gearboxDTOS = new ArrayList<>();
        for (Gearbox gearbox : gearboxes ){
            GearboxDTO gearboxDTO = new GearboxDTO();
            gearboxDTO.setId(gearbox.getId());
            gearboxDTO.setType(gearbox.getType());

            gearboxDTOS.add(gearboxDTO);
        }

        return new ResponseEntity<>(gearboxDTOS, HttpStatus.OK);
    }
}

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
public class    GearboxService {
    private GearboxRepository gearboxRepository;

    @Autowired
    public GearboxService(GearboxRepository gearboxRepository) {
        this.gearboxRepository = gearboxRepository;
    }

    public ResponseEntity<?> getAllGearboxes_ResponseEntity() {
        log.info("Gearbox Service - getAllGearboxes_ResponseEntity");

        List<Gearbox> gearboxes = getAllGearboxes();

        List<GearboxDTO> gearboxDTOS = new ArrayList<>();
        for (Gearbox g : gearboxes) {
            GearboxDTO gearboxDTO = new GearboxDTO();

            gearboxDTO.setId(g.getId());
            gearboxDTO.setType(g.getType());

            gearboxDTOS.add(gearboxDTO);
        }

        return new ResponseEntity<List<GearboxDTO>>(gearboxDTOS, HttpStatus.OK);
    }

    public List<Gearbox> getAllGearboxes() {
        log.info("Gearbox Service - getAllGearboxes()");
        return gearboxRepository.findAll();
    }
}

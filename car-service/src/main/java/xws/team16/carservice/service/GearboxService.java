package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.GearboxDTO;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.Gearbox;
import xws.team16.carservice.repository.GearboxRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class GearboxService {

    private GearboxRepository gearboxRepository;

    @Autowired
    public GearboxService(GearboxRepository gearboxRepository) {
        this.gearboxRepository = gearboxRepository;
    }

    public Gearbox getGearboxById(Long id) {
        log.info("Gearbox Service - getGearboxById(" + id + ")");
        return this.gearboxRepository.findById(id).orElseThrow(() -> new NotFoundException("Gearbox with given id was nto found"));
    }

    public ResponseEntity<?> getGearboxById_ResponseEntity(Long id) {
        log.info("Gearbox Service - getGearboxById_ResponseEntity(" + id + ")");

        Gearbox gearbox = getGearboxById(id);

        GearboxDTO gearboxDTO = new GearboxDTO();
        gearboxDTO.setId(gearbox.getId());
        gearboxDTO.setType(gearbox.getType());

        return new ResponseEntity<GearboxDTO>(gearboxDTO, HttpStatus.FOUND);
    }

    public List<Gearbox> getAllGearboxes() {
        log.info("Gearbox Service - getAllGearboxes()");
        return gearboxRepository.findAll();
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
}

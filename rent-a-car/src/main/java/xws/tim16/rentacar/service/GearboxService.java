package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.dto.GearboxDTO;
import xws.tim16.rentacar.exception.NotFoundException;
import xws.tim16.rentacar.model.Gearbox;
import xws.tim16.rentacar.repository.GearboxRepository;

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

    public Gearbox createGearbox(GearboxDTO gearboxDTO) {
        log.info("Gearbox Service - createGearbox(gearboxDTO)");

        if (gearboxRepository.getByType(gearboxDTO.getType()) == null) {
            Gearbox gearbox = new Gearbox();
            gearbox.setType(gearboxDTO.getType());

            gearboxRepository.save(gearbox);

            return gearbox;
        }

        return null;
    }

    public ResponseEntity<?> createGearbox_ResponseEntity(GearboxDTO gearboxDTO) {
        log.info("Gearbox Service - createGearbox_ResponseEntity(gearboxDTO)");

        Gearbox gearbox = createGearbox(gearboxDTO);

        if (gearbox == null) {
            return new ResponseEntity<>("Gearbox with that name already exists.", HttpStatus.CONFLICT);
        }

        gearboxDTO.setId(gearbox.getId());
        return new ResponseEntity<GearboxDTO>(gearboxDTO, HttpStatus.OK);
    }

    public Gearbox update(GearboxDTO gearboxDTO) {
        log.info("Gearbox Service - update(gearboxDTO)");

        Gearbox gearbox = gearboxRepository.getById(gearboxDTO.getId());

        if (gearbox == null)
            return null;

        gearbox.setType(gearboxDTO.getType());

        gearboxRepository.save(gearbox);

        return gearbox;
    }

    public ResponseEntity<?> update_ResponseEntity(GearboxDTO gearboxDTO) {
        log.info("Gearbox Service - update_ResponseEntity(gearboxDTO)");

        Gearbox gearbox = update(gearboxDTO);

        if (gearbox == null)
            return new ResponseEntity<>("Gearbox with given id was not found", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<GearboxDTO>(gearboxDTO, HttpStatus.OK);
    }

    public Long deleteGearboxById(Long id) {
        log.info("Gearbox Service - deleteGearboxById(" + id + ")");

        Gearbox gearbox = gearboxRepository.getById(id);

        if (gearbox == null)
            return null;

        gearboxRepository.delete(gearbox);

        return id;
    }

    public ResponseEntity<?> deleteGearboxById_ResponseEntity(Long id) {
        log.info("Gearbox Service - deleteGearboxById_ResponseEntity(" + id + ")");

        Long returnId = deleteGearboxById(id);

        if (returnId == id)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>("Gearbox with given id was not found", HttpStatus.BAD_REQUEST);
    }
}

package xws.team16.carservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.carservice.dto.MarkDTO;
import xws.team16.carservice.model.Mark;
import xws.team16.carservice.repository.MarkRepository;
import xws.team16.carservice.service.MarkService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/mark")
public class MarkController {

    private MarkService markService;

    @Autowired
    public MarkController(MarkService markService){
        this.markService = markService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        log.info("Mark Controller - get all marks.");

        List<MarkDTO> markDTOS = markService.getAll();

        return new ResponseEntity<List<MarkDTO>>(markDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        log.info("Mark Controller - get mark by id: " + id + ".");

        Mark mark = markService.getMarkById(id);

        MarkDTO markDTO = new MarkDTO();
        markDTO.setId(mark.getId());
        markDTO.setName(mark.getName());

        return new ResponseEntity<MarkDTO>(markDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createOne(@RequestBody MarkDTO markDTO){
        log.info("Mark Controller - create mark.");

        Mark mark = markService.createOne(markDTO);

        if (mark == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        markDTO.setId(mark.getId());
        return new ResponseEntity<MarkDTO>(markDTO, HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<?> update(@RequestBody MarkDTO markDTO){
        log.info("Mark Controller - update mark.");

        Mark mark = markService.update(markDTO);

        if(mark == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<MarkDTO>(markDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info("Mark Controller - delete mark (markID: " + id + ").");

        Long returnID = markService.delete(id);

        if (returnID == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

package xws.team16.carservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.carservice.dto.MarkDTO;
import xws.team16.carservice.model.Mark;
import xws.team16.carservice.repository.MarkRepository;
import xws.team16.carservice.service.MarkService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/mark")
public class MarkController {

    private MarkService markService;

    @Autowired
    public MarkController(MarkService markService){
        this.markService = markService;
    }

    @GetMapping(value = "/all")
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

}

package xws.team16.searchservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.searchservice.dto.MarkDTO;
import xws.team16.searchservice.service.MarkService;

import java.util.List;

@CrossOrigin(value = "*")
@RestController @Slf4j
@RequestMapping(value="/mark")
public class MarkController {
    private MarkService markService;

    @Autowired
    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        log.info("Mark Controller - get all marks.");

        List<MarkDTO> markDTOS = markService.getAll();

        return new ResponseEntity<List<MarkDTO>>(markDTOS, HttpStatus.OK);
    }
}

package xws.team16.searchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.searchservice.dto.MarkDTO;
import xws.team16.searchservice.model.Mark;
import xws.team16.searchservice.repository.MarkRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class MarkService {
    private MarkRepository markRepository;

    @Autowired
    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public ResponseEntity<List<MarkDTO>> getMarks() {
        log.info("Mark service - get all marks");
        List<Mark> marks = this.markRepository.findAll();

        List<MarkDTO> markDTOS = new ArrayList<>();
        for (Mark mark  : marks ){
            MarkDTO markDTO = new MarkDTO();
            markDTO.setId(mark.getId());
            markDTO.setName(mark.getName());

            markDTOS.add(markDTO);
        }

        return new ResponseEntity<>(markDTOS, HttpStatus.OK);
    }
}

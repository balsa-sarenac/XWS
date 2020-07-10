package xws.team16.searchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.searchservice.dto.MarkDTO;
import xws.team16.searchservice.dto.ModelDTO;
import xws.team16.searchservice.model.Mark;
import xws.team16.searchservice.model.Model;
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

    public List<MarkDTO> getAll(){
        log.info("Mark Service - get all marks.");

        List<Mark> marks = markRepository.findAll();

        List<MarkDTO> markDTOS = new ArrayList<>();
        for (Mark m : marks){
            MarkDTO markDTO = new MarkDTO();
            markDTO.setId(m.getId());
            markDTO.setName(m.getName());
            List<ModelDTO> modelDTOList = new ArrayList<>();
            for (Model model: m.getModels()) {
                ModelDTO modelDTO = new ModelDTO();
                modelDTO.setId(model.getId());
                modelDTO.setName(model.getName());
                modelDTOList.add(modelDTO);
            }
            markDTO.setModels(modelDTOList);
            markDTOS.add(markDTO);
        }
        return markDTOS;
    }
}

package xws.team16.searchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.searchservice.dto.MarkDTO;
import xws.team16.searchservice.dto.ModelDTO;
import xws.team16.searchservice.model.Model;
import xws.team16.searchservice.repository.ModelRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ModelService {

    private  ModelRepository modelRepository;

    @Autowired
    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public ResponseEntity<List<ModelDTO>> getModels() {
        log.info("Model service - get all models");
        List<Model> models = this.modelRepository.findAll();

        List<ModelDTO> modelDTOS = new ArrayList<>();
        for (Model model : models ){
            ModelDTO modelDTO = new ModelDTO();
            modelDTO.setId(model.getId());
            modelDTO.setName(model.getName());
            MarkDTO markDTO = new MarkDTO();
            markDTO.setId(model.getMark().getId());
            markDTO.setName(model.getMark().getName());
            modelDTO.setMark(markDTO);
            modelDTOS.add(modelDTO);
        }

        return new ResponseEntity<>(modelDTOS, HttpStatus.OK);
    }
}

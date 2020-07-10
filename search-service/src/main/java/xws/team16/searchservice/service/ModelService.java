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

    public ResponseEntity<List<ModelDTO>> getAll(){
        log.info("Model service - get all models.");

        List<Model> models = modelRepository.findAll();

        List<ModelDTO> modelDTOS = new ArrayList<>();
        for (Model m : models){
            MarkDTO markDTO = new MarkDTO();
            markDTO.setId(m.getMark().getId());
            markDTO.setName(m.getMark().getName());

            ModelDTO modelDTO = new ModelDTO();
            modelDTO.setId(m.getId());
            modelDTO.setName(m.getName());
            modelDTO.setMark(markDTO);

            modelDTOS.add(modelDTO);
        }

        return new ResponseEntity<>(modelDTOS, HttpStatus.OK);
    }
}

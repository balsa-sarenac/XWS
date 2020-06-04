package xws.team16.carservice.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.MarkDTO;
import xws.team16.carservice.dto.ModelDTO;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.Model;
import xws.team16.carservice.repository.ModelRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class ModelService {

    private ModelRepository modelRepository;

    @Autowired
    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public Model getModelById(Long modelId) {
        log.info("Model service - getting model by id");

        return this.modelRepository.findById(modelId).orElseThrow(() -> new NotFoundException("Model with given id was not found."));
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

    public ResponseEntity<ModelDTO> getById(Long id){
        log.info("Model service - get model by id: " + id + ".");

        Model model = modelRepository.getOne(id);

        if (model == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   

        MarkDTO markDTO = new MarkDTO();
        markDTO.setId(model.getMark().getId());
        markDTO.setName(model.getMark().getName());

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setId(model.getId());
        modelDTO.setName(model.getName());
        modelDTO.setMark(markDTO);

        return new ResponseEntity<>(modelDTO, HttpStatus.OK);
    }
}

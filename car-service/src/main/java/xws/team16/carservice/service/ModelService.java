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
import xws.team16.carservice.model.Mark;
import xws.team16.carservice.model.Model;
import xws.team16.carservice.repository.MarkRepository;
import xws.team16.carservice.repository.ModelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class ModelService {

    private ModelRepository modelRepository;
    private MarkRepository markRepository;

    @Autowired
    public ModelService(ModelRepository modelRepository, MarkRepository markRepository) {
        this.modelRepository = modelRepository;
        this.markRepository = markRepository;
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

    public ResponseEntity<?> createOne(ModelDTO modelDTO){
        log.info("Model service - create model.");

        Mark mark = markRepository.getByNameAndId(modelDTO.getMark().getName(), modelDTO.getMark().getId());

        if (mark == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Model model2 = modelRepository.getByName(modelDTO.getName());
        if (model2 == null){
            Model model = new Model();
            model.setName(modelDTO.getName());
            model.setMark(mark);

            modelRepository.save(model);

            modelDTO.setId(model.getId());

            return new ResponseEntity<>(modelDTO, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> update(ModelDTO modelDTO){
        log.info("Model service - update model.");

        Model model = modelRepository.getById(modelDTO.getId());

        if (model == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Mark mark = markRepository.getByNameAndId(modelDTO.getMark().getName(), modelDTO.getMark().getId());

        if (mark == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        model.setName(modelDTO.getName());
        model.setMark(mark);
        modelRepository.save(model);

        return new ResponseEntity<>(modelDTO, HttpStatus.OK);
    }
}

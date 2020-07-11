package xws.team16.carservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.carservice.dto.ModelDTO;
import xws.team16.carservice.service.ModelService;

import javax.jws.WebParam;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/model")
public class ModelController {

    private ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        log.info("Model Controller - get all models.");
        return modelService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        log.info("Model Controller - get model by id: " + id + ".");
        return modelService.getById(id);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createOne(@RequestBody ModelDTO modelDTO){
        log.info("Model Controller - create model.");
        return modelService.createOne(modelDTO);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<?> update(@RequestBody ModelDTO modelDTO){
        log.info("Model Controller - update model.");
        return modelService.update(modelDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info("Model Controller - delete model.");
        return modelService.delete(id);
    }

}

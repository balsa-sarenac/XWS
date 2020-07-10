package xws.team16.searchservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.searchservice.service.ModelService;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(value = "/model")
public class ModelController {

    private ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        log.info("Model Controller - get all models.");
        return modelService.getAll();
    }
}

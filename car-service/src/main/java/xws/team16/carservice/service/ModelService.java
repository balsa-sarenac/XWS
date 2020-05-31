package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.Model;
import xws.team16.carservice.repository.ModelRepository;

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
}

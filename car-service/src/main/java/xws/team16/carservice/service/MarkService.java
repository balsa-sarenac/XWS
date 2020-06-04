package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.MarkDTO;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.Mark;
import xws.team16.carservice.repository.MarkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class MarkService {

    private MarkRepository markRepository;

    @Autowired
    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public Mark getMarkById(Long markId) {
        log.info("Mark service - getting mark by id");
        return this.markRepository.findById(markId).orElseThrow(() -> new NotFoundException("Mark with given id was not fond."));
    }

    public List<MarkDTO> getAll(){
        log.info("Mark Service - get all marks.");

        List<Mark> marks = markRepository.findAll();

        List<MarkDTO> markDTOS = new ArrayList<>();
        for (Mark m : marks){
            MarkDTO markDTO = new MarkDTO();
            markDTO.setId(m.getId());
            markDTO.setName(m.getName());

            markDTOS.add(markDTO);
        }

        return markDTOS;
    }

    public Mark createOne(MarkDTO markDTO){
        log.info("Mark Service - create mark.");

        if (markRepository.getByName(markDTO.getName()) == null){
            Mark mark = new Mark();
            mark.setName(markDTO.getName());

            markRepository.save(mark);

            return mark;
        }

        return null;
    }

    public Mark update(MarkDTO markDTO){
        log.info("Mark Service - update mark.");

        Mark mark = markRepository.getById(markDTO.getId());

        if (mark == null){
            return null;
        }

        mark.setName(markDTO.getName());

        markRepository.save(mark);

        return mark;
    }
}

package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.Mark;
import xws.team16.carservice.repository.MarkRepository;

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
}

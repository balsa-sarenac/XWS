package xws.team16.searchservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.searchservice.service.MarkService;

@RestController @Slf4j
@RequestMapping(value="/mark")
public class MarkController {
    private MarkService markService;

    @Autowired
    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping(value="/all")
    public ResponseEntity<?> getAll() {
        log.info("Mark controller - get marks");
        return this.markService.getMarks();
    }
}

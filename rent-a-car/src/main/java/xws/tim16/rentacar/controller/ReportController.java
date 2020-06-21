package xws.tim16.rentacar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.tim16.rentacar.dto.ReportDTO;
import xws.tim16.rentacar.service.ReportService;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/report")
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> newReport (@RequestBody ReportDTO reportDTO){
        log.info("Report controller - creating new report");
        return reportService.newReport_ResponseEntity(reportDTO);
    }
}

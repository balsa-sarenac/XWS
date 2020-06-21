package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.ReportDTO;
import xws.team16.carservice.repository.ReportRepository;

@Service
@Slf4j
public class ReportService {

    private ReportRepository reportRepository;

    @Autowired
    ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }
}

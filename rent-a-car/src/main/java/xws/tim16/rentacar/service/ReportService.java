package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.dto.ReportDTO;
import xws.tim16.rentacar.model.Car;
import xws.tim16.rentacar.model.Report;
import xws.tim16.rentacar.repository.ReportRepository;

@Service
@Slf4j
public class ReportService {

    private ReportRepository reportRepository;
    private CarService carService;

    @Autowired
    ReportService(ReportRepository reportRepository, CarService carService){
        this.reportRepository = reportRepository;
        this.carService = carService;
    }

    public Report newReport(ReportDTO reportDTO){
        log.info("Report service - creating new report");

        Car car = carService.getCar(reportDTO.getCar_id());
        // if(car == null) return null;

        car = carService.updateCarsKilometrage(car, reportDTO.getKilometrage());

        Report report = new Report();
        report.setCar(car);
        report.setComment(reportDTO.getComment());
        report.setKilometrage(reportDTO.getKilometrage());

        this.reportRepository.save(report);

        return report;
    }

    public ResponseEntity<?> newReport_ResponseEntity(ReportDTO reportDTO) {
        log.info("Report service - newReport_ResponseEntity(reportDTO)");

        Report report = newReport(reportDTO);

        reportDTO.setId(report.getId());

        return new ResponseEntity<ReportDTO>(reportDTO, HttpStatus.CREATED);
    }
}

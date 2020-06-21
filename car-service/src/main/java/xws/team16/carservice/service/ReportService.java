package xws.team16.carservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.ReportDTO;
import xws.team16.carservice.model.Ad;
import xws.team16.carservice.model.Car;
import xws.team16.carservice.model.Report;
import xws.team16.carservice.repository.ReportRepository;

@Service
@Slf4j
public class ReportService {

    private ReportRepository reportRepository;
    private CarService carService;
    private AdService adService;

    @Autowired
    ReportService(ReportRepository reportRepository, CarService carService, AdService adService){
        this.reportRepository = reportRepository;
        this.carService = carService;
        this.adService = adService;
    }

    public Report newReport(ReportDTO reportDTO){
        log.info("Report service - creating new report");

        Ad ad = adService.getAdById(reportDTO.getAd_id());

        Car car = ad.getCar();
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

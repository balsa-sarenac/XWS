package xws.team16.carservice.service;

import com.sun.org.apache.regexp.internal.RE;
import https.ftn_uns_ac_rs.car.PostReportRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.ReportDTO;
import xws.team16.carservice.generated.car.PostReportResponse;
import xws.team16.carservice.generated.car.TReport;
import xws.team16.carservice.model.Car;
import xws.team16.carservice.model.Report;
import xws.team16.carservice.repository.ReportRepository;

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

        report = this.reportRepository.save(report);

        return report;
    }

    public ResponseEntity<?> newReport_ResponseEntity(ReportDTO reportDTO) {
        log.info("Report service - newReport_ResponseEntity(reportDTO)");

        Report report = newReport(reportDTO);

        reportDTO.setId(report.getId());

        return new ResponseEntity<ReportDTO>(reportDTO, HttpStatus.CREATED);
    }

    public PostReportResponse postReportSoap(TReport tReport) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(tReport.getId());
        reportDTO.setComment(tReport.getComment());
        reportDTO.setKilometrage(tReport.getKilometrage());
        reportDTO.setCar_id(tReport.getCarId());

        Report report = newReport(reportDTO);

        PostReportResponse response = new PostReportResponse();
        response.setReportResponse(report.getId());
        return response;
    }
}

package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.client.CarClient;
import xws.tim16.rentacar.dto.ReportDTO;
import xws.tim16.rentacar.generated.PostReportResponse;
import xws.tim16.rentacar.generated.TReport;
import xws.tim16.rentacar.model.Ad;
import xws.tim16.rentacar.model.Car;
import xws.tim16.rentacar.model.Report;
import xws.tim16.rentacar.repository.ReportRepository;

@Service
@Slf4j
public class ReportService {

    private ReportRepository reportRepository;
    private CarService carService;
    private AdService adService;
    private CarClient carClient;
    private BillService billService;

    @Autowired
    public ReportService(ReportRepository reportRepository, CarService carService, AdService adService, CarClient carClient, BillService billService) {
        this.reportRepository = reportRepository;
        this.carService = carService;
        this.adService = adService;
        this.carClient = carClient;
        this.billService = billService;
    }



    public Report newReport(ReportDTO reportDTO){
        log.info("Report service - creating new report");

        Ad ad = adService.getCar(reportDTO.getAd_id());
        Car car = ad.getCar();
        // if(car == null) return null;

        car = carService.updateCarsKilometrage(car, reportDTO.getKilometrage());

        Report report = new Report();
        report.setCar(car);
        report.setComment(reportDTO.getComment());
        report.setKilometrage(reportDTO.getKilometrage());

        if(ad.getAllowedKilometrage() != 0 && ad.getAllowedKilometrage() < report.getKilometrage()){
            this.billService.createBill(report.getKilometrage()-ad.getAllowedKilometrage(), ad.getPriceList(), reportDTO.getUser_id());
        }

        log.info("Requesting new report via soap");
        TReport tReport = new TReport();
        tReport.setComment(reportDTO.getComment());
        tReport.setKilometrage(reportDTO.getKilometrage());
        tReport.setCarId(reportDTO.getCar_id() == null ? 1L : reportDTO.getCar_id());
        try {
            PostReportResponse response = this.carClient.postReport(tReport);
            log.info("Soap successful - proceeding");
            report.setRefId(response.getReportResponse());
        } catch (Exception e) {
            e.printStackTrace();
        }
        report = this.reportRepository.save(report);

        return report;
    }

    public ResponseEntity<?> newReport_ResponseEntity(ReportDTO reportDTO) {
        log.info("Report service - newReport_ResponseEntity(reportDTO)");

        Report report = newReport(reportDTO);

        reportDTO.setId(report.getId());

        return new ResponseEntity<ReportDTO>(reportDTO, HttpStatus.CREATED);
    }
}

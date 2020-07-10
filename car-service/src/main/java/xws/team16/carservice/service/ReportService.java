package xws.team16.carservice.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.client.RequestClient;
import xws.team16.carservice.dto.PriceListDTO;
import xws.team16.carservice.dto.ReportDTO;
import xws.team16.carservice.generated.car.PostReportResponse;
import xws.team16.carservice.generated.car.TReport;
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
    private ModelMapper modelMapper;

    @Autowired
    private RequestClient requestClient;

    @Autowired
    ReportService(ModelMapper modelMapper, ReportRepository reportRepository, CarService carService, AdService adService){
        this.reportRepository = reportRepository;
        this.carService = carService;
        this.adService = adService;
        this.modelMapper = modelMapper;
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
            log.info("Report service - calling feign request");
            try {
                this.requestClient.createBill(report.getKilometrage()-ad.getAllowedKilometrage(),reportDTO.getUser_id(),modelMapper.map(ad.getPriceList(), PriceListDTO.class));
                log.info("Successufully called request service");
            } catch (FeignException.NotFound e) {
                log.info("Error calling request service");
            }
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

    public PostReportResponse postReportSoap(TReport tReport) {
        ReportDTO reportDTO = new ReportDTO();
//        reportDTO.setId(tReport.getId());
        reportDTO.setComment(tReport.getComment());
        reportDTO.setKilometrage(tReport.getKilometrage());
        reportDTO.setAd_id(tReport.getCarId());

        Report report = newReport(reportDTO);

        PostReportResponse response = new PostReportResponse();
        response.setReportResponse(report.getId());

        log.info("Successfully created new report for soap request");
        return response;
    }
}

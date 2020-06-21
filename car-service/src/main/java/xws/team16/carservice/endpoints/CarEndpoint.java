package xws.team16.carservice.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import xws.team16.carservice.generated.car.GetStatisticsRequest;
import xws.team16.carservice.generated.car.GetStatisticsResponse;
import xws.team16.carservice.generated.car.PostReportRequest;
import xws.team16.carservice.generated.car.PostReportResponse;
import xws.team16.carservice.model.Report;
import xws.team16.carservice.service.CarService;
import xws.team16.carservice.service.ReportService;

@Endpoint @Slf4j
public class CarEndpoint {
    private static final String NAMESPACE_URI = "https://ftn.uns.ac.rs/car";

    private CarService carService;
    private ReportService reportService;

    @Autowired
    public CarEndpoint(CarService carService, ReportService reportService) {
        this.carService = carService;
        this.reportService = reportService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStatisticsRequest")
    @ResponsePayload
    public GetStatisticsResponse getStatisticsInfo(@RequestPayload GetStatisticsRequest request) {
        log.info("Car endpoint - retrieving car statistics info");
        return this.carService.getUserCars(request.getUserId());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PostReportRequest")
    @ResponsePayload
    public PostReportResponse postReport(@RequestPayload PostReportRequest request) {
        log.info("Car endpoint - posting report from soap");
        return this.reportService.postReportSoap(request.getReportRequest());
    }
}

package xws.team16.carservice.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import xws.team16.carservice.generated.car.*;
import xws.team16.carservice.model.Report;
import xws.team16.carservice.service.CarService;
import xws.team16.carservice.service.OccupiedService;
import xws.team16.carservice.service.PriceListService;
import xws.team16.carservice.service.ReportService;

@Endpoint @Slf4j
public class CarEndpoint {
    private static final String NAMESPACE_URI = "https://ftn.uns.ac.rs/car";

    private CarService carService;
    private ReportService reportService;
    private OccupiedService occupiedService;
    private PriceListService priceListService;

    @Autowired
    public CarEndpoint(CarService carService, ReportService reportService, OccupiedService occupiedService, PriceListService priceListService) {
        this.carService = carService;
        this.reportService = reportService;
        this.occupiedService = occupiedService;
        this.priceListService = priceListService;
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PostOccupiedRequest")
    @ResponsePayload
    public PostOccupiedResponse postReport(@RequestPayload PostOccupiedRequest request) {
        log.info("Occupied endpoint - post new occupied");
        PostOccupiedResponse occupiedResponse = new PostOccupiedResponse();
        occupiedResponse.setOccupiedResponse(this.occupiedService.newOccupied(request));
        return occupiedResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PostPriceListRequest")
    @ResponsePayload
    public PostPriceListResponse postPriceList(@RequestPayload PostPriceListRequest request) {
        log.info("Car endpoint - posting priceList from soap");
        return this.priceListService.postPriceListSoap(request.getPriceListRequest());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "EditPriceListRequest")
    @ResponsePayload
    public PostPriceListResponse editPriceList(@RequestPayload EditPriceListRequest request) {
        log.info("Car endpoint - editing priceList from soap");
        return this.priceListService.editPriceListSoap(request.getPriceListRequest());
    }
}

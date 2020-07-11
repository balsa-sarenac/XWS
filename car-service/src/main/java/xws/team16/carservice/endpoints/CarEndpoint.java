package xws.team16.carservice.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import xws.team16.carservice.generated.car.*;
import xws.team16.carservice.model.Report;
import xws.team16.carservice.service.*;

@Endpoint @Slf4j
public class CarEndpoint {
    private static final String NAMESPACE_URI = "https://ftn.uns.ac.rs/car";

    private CarService carService;
    private ReportService reportService;
    private OccupiedService occupiedService;
    private PriceListService priceListService;
    private CommentService commentService;
    private GradeService gradeService;

    @Autowired
    public CarEndpoint(CarService carService, ReportService reportService, OccupiedService occupiedService, PriceListService priceListService, CommentService commentService, GradeService gradeService) {
        this.carService = carService;
        this.reportService = reportService;
        this.occupiedService = occupiedService;
        this.priceListService = priceListService;
        this.commentService = commentService;
        this.gradeService = gradeService;
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
    public EditPriceListResponse editPriceList(@RequestPayload EditPriceListRequest request) {
        log.info("Car endpoint - editing priceList from soap");
        return this.priceListService.editPriceListSoap(request.getPriceListRequest());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PostCommentRequest")
    @ResponsePayload
    public PostCommentResponse postComment(@RequestPayload PostCommentRequest request) {
        log.info("Car endpoint - posting comment from soap");
        return this.commentService.postCommentSoap(request.getCommentRequest());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetCommentRequest")
    @ResponsePayload
    public GetCommentResponse getComments(@RequestPayload GetCommentRequest request) {
        log.info("Car endpoint - posting comment from soap");
        return this.commentService.getCommentsSoap(request.getCommentRequest());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetGradeRequest")
    @ResponsePayload
    public GetGradeResponse getGrades(@RequestPayload GetGradeRequest request) {
        log.info("Car endpoint - posting comment from soap");
        return this.gradeService.getGradesSoap(request.getGradeRequest());
    }
}

package xws.tim16.rentacar.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import xws.tim16.rentacar.generated.*;

@Slf4j
public class CarClient extends WebServiceGatewaySupport {

    public PostAdResponse postNewCar(TAd tAd) {
        PostAdRequest request = new PostAdRequest();
        request.setAdRequest(tAd);

        log.info("Requesting creation of new car " + tAd.toString());

        PostAdResponse response = (PostAdResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8083/ad-soap/ad", request,
                        new SoapActionCallback("https://ftn.uns.ac.rs/ad/PostAdRequest"));

        return response;
    }

    public GetStatisticsResponse getStatistics(long userId) {
        GetStatisticsRequest request = new GetStatisticsRequest();
        request.setUserId(userId);

        log.info("Requesting car statistics for user with id " + userId);

        GetStatisticsResponse response = (GetStatisticsResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8083/ad-soap/car", request,
                        new SoapActionCallback("https://ftn.uns.ac.rs/car/GetStatisticsRequest"));
        return response;
    }

    public PostReportResponse postReport(TReport tReport) {
        PostReportRequest request = new PostReportRequest();
        request.setReportRequest(tReport);

        log.info("Requesting new report for car with id " + tReport.getCarId());

        PostReportResponse response = (PostReportResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8083/ad-soap/car", request,
                        new SoapActionCallback("https://ftn.uns.ac.rs/car/PostReportRequest"));
        return response;
    }
}

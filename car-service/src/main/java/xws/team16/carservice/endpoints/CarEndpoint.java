package xws.team16.carservice.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import xws.team16.carservice.generated.car.GetStatisticsRequest;
import xws.team16.carservice.generated.car.GetStatisticsResponse;
import xws.team16.carservice.service.CarService;

@Endpoint @Slf4j
public class CarEndpoint {
    private static final String NAMESPACE_URI = "https://ftn.uns.ac.rs/car";

    private CarService carService;

    @Autowired
    public CarEndpoint(CarService carService) {
        this.carService = carService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStatisticsRequest")
    @ResponsePayload
    public GetStatisticsResponse getStatisticsInfo(@RequestPayload GetStatisticsRequest request) {
        log.info("Car endpoint - retrieving car statistics info");
        return this.carService.getUserCars(request.getUserId());
    }
}

package xws.team16.carservice.endpoints;

import xws.team16.carservice.generated.ad.PostAdRequest;
import xws.team16.carservice.generated.ad.PostAdResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import xws.team16.carservice.service.AdService;

@Endpoint @Slf4j
public class AdEndpoint {
    private static final String NAMESPACE_URI = "https://ftn.uns.ac.rs/ad";

    private AdService adService;

    @Autowired
    public AdEndpoint(AdService adService) {
        this.adService = adService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PostAdRequest")
    @ResponsePayload
    public PostAdResponse newAd(@RequestPayload PostAdRequest adRequest) {
        log.info("Ad endpoint - post new ad");
        PostAdResponse adResponse = new PostAdResponse();
        adResponse.setAdResponse(this.adService.newAd(adRequest));
        return adResponse;
    }
}

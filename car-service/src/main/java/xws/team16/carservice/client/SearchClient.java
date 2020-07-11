package xws.team16.carservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xws.team16.carservice.dto.AdDTO;

import java.util.List;

@FeignClient(name = "search")
public interface SearchClient {

    @PostMapping(value = "/ad/remove")
    Void removeAds(@RequestBody List<Long> ads);

    @PostMapping(value = "/ad")
    Void postAd(@RequestBody AdDTO adDTO);
}

package xws.team16.carservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xws.team16.carservice.dto.PriceListDTO;

import java.util.List;

@FeignClient(name = "search")
public interface SearchClient {

    @PostMapping(value = "/ad/remove")
    Void removeAds(@RequestBody List<Long> ads);

    @PostMapping(value = "/priceList")
    Void createPriceList(@RequestBody PriceListDTO priceListDTO);

    @PutMapping( value = "/priceList", consumes = "application/json")
    public ResponseEntity<?> editPriceList(@RequestBody PriceListDTO priceListDTO);
}

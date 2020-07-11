package xws.team16.carservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xws.team16.carservice.dto.AdDTO;
import xws.team16.carservice.dto.NewAdRequestDTO;
import xws.team16.carservice.dto.OccupiedDTO;
import xws.team16.carservice.dto.PriceListDTO;
import xws.team16.carservice.model.PriceList;

@FeignClient(name = "request")
public interface RequestClient {

    @PostMapping(value = "/occupied", consumes = "application/json")
    Void occupiedRequests(@RequestBody OccupiedDTO occupiedDTO);

    @PostMapping(value = "/bill/{kilometrage}/{user_id}")
    Void createBill(@PathVariable double kilometrage, @PathVariable Long user_id, @RequestBody PriceListDTO priceList);

    @GetMapping(value = "/cancelRequest/{id}", consumes = "application/json")
    Void cancelRequest(@PathVariable Long id);

    @PostMapping(value = "/ad")
    Void postAd(@RequestBody NewAdRequestDTO adDTO);
}

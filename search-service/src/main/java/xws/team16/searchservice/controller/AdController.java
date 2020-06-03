package xws.team16.searchservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.searchservice.dto.SearchDTO;
import xws.team16.searchservice.service.AdService;

@Slf4j
@RestController
@RequestMapping(value = "/ad")
public class AdController {

    private AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @PostMapping(value = "/search/{page}")
    public ResponseEntity<?> searchAds(@RequestBody SearchDTO search,@PathVariable int page) {
        log.info("Ad controller - searching ads");
        return this.adService.searchAds(search,page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAd(@PathVariable Long id) {
        log.info("Ad controller - get ad");
        return this.adService.getAd(id);
    }



}

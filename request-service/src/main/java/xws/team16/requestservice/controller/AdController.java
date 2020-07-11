package xws.team16.requestservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.team16.requestservice.dto.NewAdRequestDTO;
import xws.team16.requestservice.service.AdService;

@RestController @Slf4j
@RequestMapping(value = "/ad")
public class AdController {

    private AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @PostMapping
    public ResponseEntity<?> newAd(@RequestBody NewAdRequestDTO adDTO) {
        log.info("Creating new ad");
        return this.adService.newAd(adDTO);
    }
}

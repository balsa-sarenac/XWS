package xws.tim16.rentacar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.tim16.rentacar.dto.AdDTO;
import xws.tim16.rentacar.service.AdService;

@Slf4j
@RestController
@RequestMapping(value = "/ad")
public class AdController {

    private AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    /**
     * Creates new Ad and Car
     *
     * @param adDTO is ad to be created with all fields
     */
    @PostMapping
    public ResponseEntity<?> addNew(@RequestBody AdDTO adDTO) {
        log.info("Ad controller - new ad");
        return this.adService.newAd(adDTO);
    }
}

package xws.tim16.rentacar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.tim16.rentacar.dto.AdDTO;
import xws.tim16.rentacar.dto.SearchDTO;
import xws.tim16.rentacar.service.AdService;

import java.sql.SQLException;

@CrossOrigin
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
    public ResponseEntity<?> addNew(@RequestBody AdDTO adDTO) throws SQLException {
        log.info("Ad controller - new ad");
        return this.adService.newAd(adDTO);
    }

    /**
     * Get ad with specific id
     * @param id is id of the ad
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAd(@PathVariable Long id) {
        log.info("Ad controller - get ad");
        return this.adService.getOneAdById(id);
    }

    @PostMapping(value = "/search/{page}/{sort}")
    public ResponseEntity<?> searchAds(@RequestBody SearchDTO search, @PathVariable int page,@PathVariable String sort) {
        log.info("Ad controller - searching ads");
        return this.adService.searchAds(search,page, sort);
    }

    @GetMapping( value = "/city")
    public ResponseEntity<?> searchCity() {
        log.info("Ad controller - searching cities");
        return this.adService.findCities();
    }

}

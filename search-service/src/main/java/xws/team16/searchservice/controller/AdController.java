package xws.team16.searchservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.searchservice.dto.AdDTO;
import xws.team16.searchservice.dto.NewAdRequestDTO;
import xws.team16.searchservice.dto.SearchDTO;
import xws.team16.searchservice.model.Ad;
import xws.team16.searchservice.service.AdService;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(value = "/ad")
public class AdController {

    private AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @PostMapping(value = "/search/{page}/{sort}")
    public ResponseEntity<?> searchAds(@RequestBody SearchDTO search, @PathVariable int page,@PathVariable String sort) throws SQLException {
        log.info("Ad controller - searching ads");
        return this.adService.searchAds(search,page, sort);
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


    @PostMapping(value = "/removeAds")
    public ResponseEntity<?> removeAds(@RequestBody Set<Ad> ads){
        log.info("Ad controller - removing ads");
        return this.adService.deleteAds(ads);
    }

    @GetMapping( value = "/city")
    public ResponseEntity<?> searchCity() {
        log.info("Ad controller - searching cities");
        return this.adService.findCities();
    }

    @PostMapping(value = "/remove")
    public ResponseEntity<?> removeAds(@RequestBody List<Long> ads) {
        log.info("Ad controller - removing ads");
        return this.adService.remove(ads);
    }

    /**
     * Creates new Ad and Car
     * @param adDTO is ad to be created with all fields
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNew(@RequestBody NewAdRequestDTO adDTO) throws SQLException {
        log.info("Ad controller - new ad");
        return this.adService.newAd(adDTO);
    }

}
